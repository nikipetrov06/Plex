package com.scalefocus.java.plexnikolaypetrov.controllers.handler;

import com.scalefocus.java.plexnikolaypetrov.dtos.ErrorDto;
import com.scalefocus.java.plexnikolaypetrov.exceptions.BadRequestException;
import com.scalefocus.java.plexnikolaypetrov.exceptions.FileException;
import java.time.LocalDateTime;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;


import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GlobalExceptionHandlerTest {

  @InjectMocks
  private GlobalExceptionHandler globalExceptionHandler;

  private LocalDateTime now;

  @Before
  public void init() {
    now = LocalDateTime.now();
  }

  @Test
  public void test_handleBadRequestException() {
    //Given
    BadRequestException badRequestException = new BadRequestException("Wrong user or password");

    //When
    ErrorDto expectedErrorDto = new ErrorDto("Wrong user or password", HttpStatus.BAD_REQUEST, now);
    ErrorDto actualErrorDto = globalExceptionHandler.handleBadRequests(badRequestException);

    //Then
    assertThat(actualErrorDto.getMessage(), containsString(expectedErrorDto.getMessage()));
    assertThat(actualErrorDto.getStatus(), equalTo(expectedErrorDto.getStatus()));
  }

  @Test
  public void test_methodArgumentNotValid() {
    //Given
    MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
    BindingResult bindingResult = mock(BindingResult.class);
    when(bindingResult.getFieldErrors())
        .thenReturn(Arrays.asList(new FieldError("User", "username", "should not be empty")));
    when(exception.getBindingResult()).thenReturn(bindingResult);

    //when
    ErrorDto errorDto = globalExceptionHandler.handleMethodArgumentNotValidException(exception);

    //then
    String expectedMessage = "{username=should not be empty}";
    assertThat(errorDto.getStatus(), is(equalTo(HttpStatus.BAD_REQUEST)));
    assertThat(errorDto.getMessage(), is(equalTo(expectedMessage)));
  }

  @Test
  public void test_fileExceptionhandler() {
    //Given
    FileException fileException = new FileException("Wrong file type");

    //When
    ErrorDto expected = new ErrorDto("Wrong file type", HttpStatus.NOT_ACCEPTABLE,now);
    ErrorDto actual = globalExceptionHandler.handleFileException(fileException);

    //Then
    assertThat(actual.getMessage(), is(expected.getMessage()));
    assertThat(actual.getStatus(), is(expected.getStatus()));
  }
}
