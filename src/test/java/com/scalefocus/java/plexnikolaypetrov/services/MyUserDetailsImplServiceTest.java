package com.scalefocus.java.plexnikolaypetrov.services;

import com.scalefocus.java.plexnikolaypetrov.entities.primary.User;
import com.scalefocus.java.plexnikolaypetrov.exceptions.BadRequestException;
import com.scalefocus.java.plexnikolaypetrov.mappers.UserMapper;
import com.scalefocus.java.plexnikolaypetrov.models.UserDetailsImpl;
import com.scalefocus.java.plexnikolaypetrov.repositories.primary.UserRepository;
import com.scalefocus.java.plexnikolaypetrov.services.primary.impl.UserDetailsServiceImpl;
import com.scalefocus.java.plexnikolaypetrov.utils.TestConstants;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MyUserDetailsImplServiceTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private UserMapper userDetailsMapper;

  @InjectMocks
  private UserDetailsServiceImpl userDetailsServiceImpl;

  @Test
  public void test_loadUserByUsername_whenItReturnsUser() {
    //Given
    when(userRepository.findByUsername(anyString()))
        .thenReturn(Optional.of(new User(null, TestConstants.USERNAME, TestConstants.PASSWORD, TestConstants.EMAIL)));

    //When
    UserDetailsImpl userDetails = userDetailsServiceImpl.loadUserByUsername(TestConstants.EMAIL);

    //Then
    assertThat(userDetails, is(notNullValue()));
  }

  @Test
  public void test_loadUserByUsername_whenItReturnsException() {
    //When
    Exception exception = assertThrows(BadRequestException.class,
        () -> userDetailsServiceImpl.loadUserByUsername("123"));

    //Then
    String expectedMessage = "User with that username doesn't exist";
    String actualMessage = exception.getMessage();
    assertThat(actualMessage, equalToIgnoringCase(expectedMessage));
  }
}
