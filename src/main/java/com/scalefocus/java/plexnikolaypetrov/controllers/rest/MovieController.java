package com.scalefocus.java.plexnikolaypetrov.controllers.rest;

import com.scalefocus.java.plexnikolaypetrov.dtos.MovieToListDto;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieController {

  @GetMapping("/movies/page/{}")
  public List<MovieToListDto> getPageOfMovies() {

  }
}
