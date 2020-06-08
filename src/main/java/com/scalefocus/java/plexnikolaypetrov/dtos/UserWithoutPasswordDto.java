package com.scalefocus.java.plexnikolaypetrov.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserWithoutPasswordDto {

  private String email;
  private String username;
}
