package com.scalefocus.java.plexnikolaypetrov.dtos;

import com.scalefocus.java.plexnikolaypetrov.utils.constants.ValidationConstants;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserDto {

  @NotBlank
  @Pattern(regexp = ValidationConstants.EMAIL, message = "Invalid e-mail address")
  private String email;

  @NotBlank
  private String username;

  @NotBlank
  @Pattern(regexp = ValidationConstants.PASSWORD, message = "Invalid password !")
  private String password;

  @NotBlank
  @Pattern(regexp = ValidationConstants.PASSWORD, message = "Invalid confirm password !")
  private String confirmPassword;
}
