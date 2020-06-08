package com.scalefocus.java.plexnikolaypetrov.utils;

import com.scalefocus.java.plexnikolaypetrov.models.UserDetailsImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class JwtUtilsTest {

  @Spy
  private JwtUtil jwtUtil;
  private UserDetailsImpl userDetailsImpl;

  @Before
  public void init() {
    userDetailsImpl = new UserDetailsImpl(TestConstants.USERNAME, TestConstants.PASSWORD, TestConstants.EMAIL);
    ReflectionTestUtils.setField(jwtUtil, "secretKey", "secret");
    ReflectionTestUtils.setField(jwtUtil, "expirationHours", 10);
  }

  @Test
  public void test_generateToken() {

    String jwt = jwtUtil.generateToken(userDetailsImpl);

    assertThat(jwt, is(notNullValue()));
  }

  @Test
  public void test_validateToken() {
    //Given
    String token = jwtUtil.generateToken(userDetailsImpl);

    //When
    final boolean isValid = jwtUtil.validateToken(token);

    //Then
    assertThat(isValid, is(true));
  }

  @Test
  public void test_validateToken_withEmptyToken() {

    //When
    final boolean isValid = jwtUtil.validateToken("");

    //Then
    assertThat(isValid, is(false));
  }

  @Test
  public void test_extractUsername() {
    String expected = "user";
    String token = jwtUtil.generateToken(userDetailsImpl);

    final String actual = jwtUtil.extractUsername(token);

    assertThat(actual, equalTo(expected));
  }

  @Test
  public void test_getExpirationIntoSeconds() {
    long expected = 36000L;

    long actual = jwtUtil.getExpirationIntoSeconds();

    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void test_validateToken_whenThrowsException() {

    boolean actual = jwtUtil.validateToken(TestConstants.JWT);

    assertThat(actual, is(false));
  }
}
