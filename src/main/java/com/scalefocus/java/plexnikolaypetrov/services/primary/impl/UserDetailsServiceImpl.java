package com.scalefocus.java.plexnikolaypetrov.services.primary.impl;

import com.scalefocus.java.plexnikolaypetrov.exceptions.BadRequestException;
import com.scalefocus.java.plexnikolaypetrov.models.UserDetailsImpl;
import com.scalefocus.java.plexnikolaypetrov.repositories.primary.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetailsImpl loadUserByUsername(final String username) {
    return userRepository.findByUsername(username)
        .map(user -> new UserDetailsImpl(user.getUsername(), user.getPassword(), user.getEmail()))
        .orElseThrow(() -> new BadRequestException("User with that username doesn't exist"));
  }
}
