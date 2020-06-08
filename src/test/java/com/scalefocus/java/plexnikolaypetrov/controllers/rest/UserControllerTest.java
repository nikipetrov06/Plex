package com.scalefocus.java.plexnikolaypetrov.controllers.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scalefocus.java.plexnikolaypetrov.dtos.LoginUserDto;
import com.scalefocus.java.plexnikolaypetrov.dtos.RegisterUserDto;
import com.scalefocus.java.plexnikolaypetrov.dtos.UserWithoutPasswordDto;
import com.scalefocus.java.plexnikolaypetrov.services.primary.impl.UserServiceImpl;
import com.scalefocus.java.plexnikolaypetrov.utils.HttpCookieUtil;
import com.scalefocus.java.plexnikolaypetrov.utils.JwtUtil;
import com.scalefocus.java.plexnikolaypetrov.utils.TestConstants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

  @Spy
  @InjectMocks
  private UserController controller;

  @Mock
  private UserServiceImpl userServiceImpl;

  @Mock
  private HttpCookieUtil httpCookieUtil;

  @Mock
  private JwtUtil jwtUtil;

  private MockMvc mockMvc;

  private ObjectMapper mapper;

  @Before
  public void init() {
    mockMvc = standaloneSetup(controller).build();
    mapper = new ObjectMapper();
  }

  @Test
  public void test_login() throws Exception {
    //Given
    LoginUserDto loginUserDto = new LoginUserDto(TestConstants.EMAIL, TestConstants.PASSWORD);
    when(userServiceImpl.loginUser(any(LoginUserDto.class))).thenReturn(TestConstants.COOKIE);

    //Then
    mockMvc.perform(post("/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(loginUserDto)))
        .andExpect(status().isOk());
  }

  @Test
  public void test_register() throws Exception {
    //Given
    RegisterUserDto registerUserDto =
        new RegisterUserDto(TestConstants.EMAIL, TestConstants.USERNAME, TestConstants.PASSWORD, TestConstants.PASSWORD);
    when(userServiceImpl.registerUser(any(RegisterUserDto.class)))
        .thenReturn(new UserWithoutPasswordDto(registerUserDto.getEmail(), registerUserDto.getUsername()));

    //Then
    mockMvc.perform(post("/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(registerUserDto)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.email").value(TestConstants.EMAIL))
        .andExpect(jsonPath("$.username").value(TestConstants.USERNAME));
  }
}
