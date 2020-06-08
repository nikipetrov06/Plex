package com.scalefocus.java.plexnikolaypetrov;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableEncryptableProperties
@SpringBootApplication
public class PlexNikolayPetrovApplication {

  public static void main(String[] args) {
    SpringApplication.run(PlexNikolayPetrovApplication.class, args);
  }
}
