package com.scalefocus.java.plexnikolaypetrov.repositories.primary;

import com.scalefocus.java.plexnikolaypetrov.entities.primary.Movie;
import java.util.Optional;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Movie Entity
 */
@Repository
public interface MoviesRepository extends PagingAndSortingRepository<Movie, String> {

  Optional<Movie> findByTitleAndYear(String title, Integer year);
}
