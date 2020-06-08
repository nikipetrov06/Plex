package com.scalefocus.java.plexnikolaypetrov.utils;

import com.scalefocus.java.plexnikolaypetrov.models.UserDetailsImpl;
import java.util.Objects;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

@Component
@RequiredArgsConstructor
public class HttpCookieUtil {

  public static final String AUTHORIZATION_COOKIE = "AuthorizationCookie";

  private final JwtUtil jwtUtil;

  public HttpCookie createCookie(final UserDetailsImpl userDetailsImpl) {
    String jwt = jwtUtil.generateToken(userDetailsImpl);
    return ResponseCookie.from(AUTHORIZATION_COOKIE, jwt)
        .maxAge(jwtUtil.getExpirationIntoSeconds())
        .httpOnly(true)
        .build();
  }

  public String getJwtFromRequest(final HttpServletRequest request) {
    final Cookie cookie = WebUtils.getCookie(request, AUTHORIZATION_COOKIE);
    return Objects.isNull(cookie) ? null : cookie.getValue();
  }
}
