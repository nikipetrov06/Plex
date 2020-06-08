package com.scalefocus.java.plexnikolaypetrov.services.primary;

import com.scalefocus.java.plexnikolaypetrov.dtos.LoginUserDto;
import com.scalefocus.java.plexnikolaypetrov.dtos.RegisterUserDto;
import com.scalefocus.java.plexnikolaypetrov.dtos.UserWithoutPasswordDto;

/**
 * Definition of UserService methods
 */

public interface UserService {

  String loginUser(final LoginUserDto loginUserDto);

  UserWithoutPasswordDto registerUser(final RegisterUserDto registerUserDto);
}
