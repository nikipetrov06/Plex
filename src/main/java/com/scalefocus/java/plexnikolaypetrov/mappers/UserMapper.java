package com.scalefocus.java.plexnikolaypetrov.mappers;

import com.scalefocus.java.plexnikolaypetrov.dtos.RegisterUserDto;
import com.scalefocus.java.plexnikolaypetrov.dtos.UserWithoutPasswordDto;
import com.scalefocus.java.plexnikolaypetrov.entities.primary.User;
import com.scalefocus.java.plexnikolaypetrov.models.UserDetailsImpl;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Definition of mapper methords
 */

@Mapper(componentModel = "spring")
public abstract class UserMapper {

  @Autowired
  protected PasswordEncoder passwordEncoder;

  public abstract UserDetailsImpl userToUserDetailsImpl(User user);

  public abstract UserWithoutPasswordDto userToUserWithoutPasswordDto(User user);

  @Mapping(target = "password", expression = "java(passwordEncoder.encode(registerUserDto.getPassword()))")
  public abstract User registerUserDtoToUser(RegisterUserDto registerUserDto);
}
