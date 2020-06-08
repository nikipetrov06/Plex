package com.scalefocus.java.plexnikolaypetrov.dtos;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDto {

  private String message;

  private HttpStatus status;

  private LocalDateTime time;

}