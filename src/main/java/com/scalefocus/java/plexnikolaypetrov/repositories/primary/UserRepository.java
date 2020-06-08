package com.scalefocus.java.plexnikolaypetrov.repositories.primary;

import com.scalefocus.java.plexnikolaypetrov.entities.primary.User;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Definition of UserRepository methods
 */
@Repository
public interface UserRepository extends CrudRepository<User, String> {

  Optional<User> findByUsername(String username);

  boolean existsByEmail(String email);

  boolean existsByEmailOrUsername(String email, String username);
}
