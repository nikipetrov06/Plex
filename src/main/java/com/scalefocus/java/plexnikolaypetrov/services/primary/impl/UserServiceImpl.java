package com.scalefocus.java.plexnikolaypetrov.services.primary.impl;

import com.scalefocus.java.plexnikolaypetrov.dtos.LoginUserDto;
import com.scalefocus.java.plexnikolaypetrov.dtos.RegisterUserDto;
import com.scalefocus.java.plexnikolaypetrov.dtos.UserWithoutPasswordDto;
import com.scalefocus.java.plexnikolaypetrov.entities.primary.User;
import com.scalefocus.java.plexnikolaypetrov.exceptions.BadRequestException;
import com.scalefocus.java.plexnikolaypetrov.mappers.UserMapper;
import com.scalefocus.java.plexnikolaypetrov.models.UserDetailsImpl;
import com.scalefocus.java.plexnikolaypetrov.repositories.primary.UserRepository;
import com.scalefocus.java.plexnikolaypetrov.services.primary.UserService;
import com.scalefocus.java.plexnikolaypetrov.utils.HttpCookieUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final HttpCookieUtil httpCookieUtil;
  private final AuthenticationManager authenticationManager;

  @Override
  public String loginUser(final LoginUserDto loginUser) {
    final Authentication authenticate =
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));
    final UserDetailsImpl userDetails = (UserDetailsImpl) authenticate.getPrincipal();
    return httpCookieUtil.createCookie(userDetails).toString();
  }

  @Override
  public UserWithoutPasswordDto registerUser(final RegisterUserDto registerUserDto) {
    if (userRepository.existsByEmailOrUsername(registerUserDto.getEmail(), registerUserDto.getUsername())) {
      throw new BadRequestException("User with this email or username already exists");
    }
    final User user = userMapper.registerUserDtoToUser(registerUserDto);
    return userMapper.userToUserWithoutPasswordDto(userRepository.save(user));
  }
}
