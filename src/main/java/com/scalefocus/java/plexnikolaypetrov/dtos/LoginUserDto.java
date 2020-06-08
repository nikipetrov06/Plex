package com.scalefocus.java.plexnikolaypetrov.dtos;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserDto {

  @NotBlank
  private String username;

  @NotBlank
  private String password;

}
