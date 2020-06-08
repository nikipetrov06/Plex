package com.scalefocus.java.plexnikolaypetrov.controllers.handler;

import com.scalefocus.java.plexnikolaypetrov.dtos.ErrorDto;
import com.scalefocus.java.plexnikolaypetrov.exceptions.BadRequestException;
import com.scalefocus.java.plexnikolaypetrov.exceptions.FileException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(BadRequestException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorDto handleBadRequests(BadRequestException exception) {
    log.error("User is trying to log in with already existing one or one that doesn't exist", exception);
    return convertExceptionToErrorDto(HttpStatus.BAD_REQUEST, exception.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorDto handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
    final Map<String, String> errorMessages = exception.getBindingResult().getFieldErrors().stream()
        .collect(Collectors.toMap(FieldError::getField, DefaultMessageSourceResolvable::getDefaultMessage));
    log.error("User didn't pass the validations", exception);
    return convertExceptionToErrorDto(HttpStatus.BAD_REQUEST, errorMessages.toString());
  }

  @ExceptionHandler(FileException.class)
  @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
  public ErrorDto handleFileException(FileException exception) {
    log.error("Something went wrong with file uploading", exception);
    return convertExceptionToErrorDto(HttpStatus.NOT_ACCEPTABLE, exception.getMessage());
  }

  private static ErrorDto convertExceptionToErrorDto(final HttpStatus status, final String message) {
    return new ErrorDto(message, status, LocalDateTime.now());
  }
}
