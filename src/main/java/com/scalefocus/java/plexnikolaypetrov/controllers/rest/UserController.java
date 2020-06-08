package com.scalefocus.java.plexnikolaypetrov.controllers.rest;

import com.scalefocus.java.plexnikolaypetrov.dtos.LoginUserDto;
import com.scalefocus.java.plexnikolaypetrov.dtos.RegisterUserDto;
import com.scalefocus.java.plexnikolaypetrov.dtos.UserWithoutPasswordDto;
import com.scalefocus.java.plexnikolaypetrov.services.primary.UserService;
import com.scalefocus.java.plexnikolaypetrov.utils.HttpCookieUtil;
import com.scalefocus.java.plexnikolaypetrov.utils.JwtUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final HttpCookieUtil httpCookieUtil;
  private final JwtUtil jwtUtil;

  @PostMapping("/login")
  @ResponseStatus(HttpStatus.OK)
  public void login(@Valid @RequestBody final LoginUserDto loginUser, final HttpServletResponse response,
      final HttpServletRequest request) {
    if (!jwtUtil.validateToken(httpCookieUtil.getJwtFromRequest(request))) {
      response.addHeader(HttpHeaders.SET_COOKIE, userService.loginUser(loginUser));
    }
  }

  @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
  public UserWithoutPasswordDto register(@Valid @RequestBody final RegisterUserDto registerUserDto) {
    return userService.registerUser(registerUserDto);
  }
}
