package com.scalefocus.java.plexnikolaypetrov.utils;

import com.scalefocus.java.plexnikolaypetrov.models.UserDetailsImpl;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HttpCookieUtilTest {

  @InjectMocks
  private HttpCookieUtil httpCookieUtil;

  @Mock
  private JwtUtil jwtUtil;

  @Test
  public void test_createCookie() {
    UserDetailsImpl userDetailsImpl = new UserDetailsImpl(TestConstants.USERNAME, TestConstants.PASSWORD, TestConstants.EMAIL);
    when(jwtUtil.generateToken(any(UserDetailsImpl.class))).thenReturn(TestConstants.JWT);
    String jwt = TestConstants.JWT;

    HttpCookie expected = ResponseCookie.from(TestConstants.TEST_COOKIE_NAME, jwt).maxAge(1L).httpOnly(true).build();
    HttpCookie actual = httpCookieUtil.createCookie(userDetailsImpl);

    assertThat(expected.getName(), equalTo(actual.getName()));
    assertThat(expected.getValue(), equalTo(actual.getValue()));
  }

  @Test
  public void test_getCookie() {
    //Given
    HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
    Cookie cookie = new Cookie(TestConstants.TEST_COOKIE_NAME, TestConstants.JWT);
    when(httpServletRequest.getCookies()).thenReturn(new Cookie[] {cookie});

    //When
    String actualCookie = httpCookieUtil.getJwtFromRequest(httpServletRequest);

    //Then
    assertThat(actualCookie, notNullValue());
  }
}
