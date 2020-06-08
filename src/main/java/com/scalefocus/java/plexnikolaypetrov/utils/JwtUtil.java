package com.scalefocus.java.plexnikolaypetrov.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtUtil {

  @Value("${jwt.expiration-hours}")
  private long expirationHours;

  @Value("${jwt.secret-key}")
  private String secretKey;

  public String extractUsername(final String token) {
    return extractClaim(token, Claims::getSubject);
  }


  public <T> T extractClaim(final String token, final Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver
        .apply(claims);
  }

  private Claims extractAllClaims(final String token) {
    return Jwts.parser()
        .setSigningKey(secretKey)
        .parseClaimsJws(token)
        .getBody();
  }

  public String generateToken(final UserDetails userDetails) {
    return createToken(new HashMap<>(), userDetails.getUsername());
  }

  private String createToken(final Map<String, Object> claims, final String subject) {

    return Jwts.builder()
        .setClaims(claims)
        .setSubject(subject)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(expirationHours)))
        .signWith(SignatureAlgorithm.HS256, secretKey).compact();
  }

  public boolean validateToken(final String token) {
    if (StringUtils.isBlank(token)) {
      return false;
    }
    try {
      Jwts.parser()
          .setSigningKey(secretKey)
          .parseClaimsJws(token);
      return true;
    } catch (SignatureException
        | MalformedJwtException
        | ExpiredJwtException
        | UnsupportedJwtException
        | IllegalArgumentException exception) {
      log.error(exception.getMessage());
    }
    return false;
  }

  public long getExpirationIntoSeconds() {
    return TimeUnit.HOURS.toSeconds(expirationHours);
  }
}
