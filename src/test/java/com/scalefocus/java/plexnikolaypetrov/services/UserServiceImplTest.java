package com.scalefocus.java.plexnikolaypetrov.services;

import com.scalefocus.java.plexnikolaypetrov.dtos.LoginUserDto;
import com.scalefocus.java.plexnikolaypetrov.dtos.RegisterUserDto;
import com.scalefocus.java.plexnikolaypetrov.dtos.UserWithoutPasswordDto;
import com.scalefocus.java.plexnikolaypetrov.entities.primary.User;
import com.scalefocus.java.plexnikolaypetrov.exceptions.BadRequestException;
import com.scalefocus.java.plexnikolaypetrov.mappers.UserMapper;
import com.scalefocus.java.plexnikolaypetrov.models.UserDetailsImpl;
import com.scalefocus.java.plexnikolaypetrov.repositories.primary.UserRepository;
import com.scalefocus.java.plexnikolaypetrov.services.primary.impl.UserServiceImpl;
import com.scalefocus.java.plexnikolaypetrov.utils.HttpCookieUtil;
import com.scalefocus.java.plexnikolaypetrov.utils.JwtUtil;
import com.scalefocus.java.plexnikolaypetrov.utils.TestConstants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;


import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

  @InjectMocks
  private UserServiceImpl userServiceImpl;

  @Mock
  private UserRepository userRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @Mock
  private UserMapper userDetailsMapper;

  @Mock
  private HttpCookieUtil httpCookieUtil;

  @Mock
  private JwtUtil jwtUtil;

  @Mock
  private LoginUserDto loginUserDto;

  @Mock
  private User user;

  @Mock
  private RegisterUserDto registerUserDto;

  @Mock
  private AuthenticationManager authenticationManager;

  @Mock
  private Authentication authentication;


  @Before
  public void init() {
    when(loginUserDto.getUsername()).thenReturn(TestConstants.USERNAME);
    when(loginUserDto.getPassword()).thenReturn(TestConstants.PASSWORD);
    when(registerUserDto.getEmail()).thenReturn(TestConstants.EMAIL);
  }

  @Test
  public void test_loginUser() {
    //Given
    UserDetailsImpl userDetails = new UserDetailsImpl(TestConstants.USERNAME, TestConstants.PASSWORD, TestConstants.EMAIL);
    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
        .thenReturn(authentication);
    when(authentication.getPrincipal()).thenReturn(userDetails);
    when(httpCookieUtil.createCookie(any(UserDetailsImpl.class)))
        .thenReturn(new HttpCookie(TestConstants.TEST_COOKIE_NAME, TestConstants.JWT));

    //When
    String actualCookie = userServiceImpl.loginUser(loginUserDto);

    //Then
    assertThat(actualCookie, is(notNullValue()));
  }

  @Test
  public void test_registerUser() {
    //Given
    when(userDetailsMapper.registerUserDtoToUser(any(RegisterUserDto.class)))
        .thenReturn(user);
    when(userDetailsMapper.userToUserWithoutPasswordDto(any(User.class)))
        .thenReturn(new UserWithoutPasswordDto(TestConstants.EMAIL, TestConstants.USERNAME));
    when(userRepository.save(any(User.class)))
        .thenReturn(user);
    final UserWithoutPasswordDto userWithoutPasswordDto = new UserWithoutPasswordDto(TestConstants.EMAIL, TestConstants.USERNAME);

    //When
    final UserWithoutPasswordDto actual = userServiceImpl.registerUser(registerUserDto);

    //Then
    assertThat(actual.getEmail(), equalTo(userWithoutPasswordDto.getEmail()));
  }

  @Test
  public void test_userAlreadyExistsRegisterUser() {
    //Given
    when(userRepository.existsByEmailOrUsername(registerUserDto.getEmail(), registerUserDto.getUsername())).thenReturn(true);

    //When
    Exception exception = assertThrows(BadRequestException.class,
        () -> userServiceImpl.registerUser(registerUserDto));

    //Then
    String expectedMessage = "User with this email or username already exists";
    String actualMessage = exception.getMessage();
    assertThat(actualMessage, containsString(expectedMessage));
  }
}
