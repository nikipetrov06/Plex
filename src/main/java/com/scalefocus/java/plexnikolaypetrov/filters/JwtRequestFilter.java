package com.scalefocus.java.plexnikolaypetrov.filters;

import com.scalefocus.java.plexnikolaypetrov.models.UserDetailsImpl;
import com.scalefocus.java.plexnikolaypetrov.services.primary.impl.UserDetailsServiceImpl;
import com.scalefocus.java.plexnikolaypetrov.utils.HttpCookieUtil;
import com.scalefocus.java.plexnikolaypetrov.utils.JwtUtil;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;

  private final UserDetailsServiceImpl userDetailsService;

  private final HttpCookieUtil httpCookieUtil;

  @Override
  protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
      throws ServletException, IOException {
    final String jwt = httpCookieUtil.getJwtFromRequest(httpServletRequest);
    if (jwtUtil.validateToken(jwt)) {
      final UserDetailsImpl userDetailsImpl = userDetailsService.loadUserByUsername(jwtUtil.extractUsername(jwt));
      final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
          new UsernamePasswordAuthenticationToken(userDetailsImpl, null, userDetailsImpl.getAuthorities());
      usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
      SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }

    filterChain.doFilter(httpServletRequest, httpServletResponse);
  }
}
