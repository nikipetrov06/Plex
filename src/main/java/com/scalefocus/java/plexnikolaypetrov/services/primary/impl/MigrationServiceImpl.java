package com.scalefocus.java.plexnikolaypetrov.services.primary.impl;

import com.scalefocus.java.plexnikolaypetrov.entities.primary.Movie;
import com.scalefocus.java.plexnikolaypetrov.enums.MetadataType;
import com.scalefocus.java.plexnikolaypetrov.mappers.MovieMapper;
import com.scalefocus.java.plexnikolaypetrov.repositories.primary.MoviesRepository;
import com.scalefocus.java.plexnikolaypetrov.repositories.secondary.MetadataItemRepository;
import com.scalefocus.java.plexnikolaypetrov.services.primary.MigrationService;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class MigrationServiceImpl implements MigrationService {

  public static final int BATCH_SIZE = 500;
  private final MetadataItemRepository metadataItemRepository;
  private final MovieMapper movieMapper;
  private final MoviesRepository moviesRepository;
  private final EntityManager secondaryDbEntityManager;
  private final Set<Movie> movies;

  @Value("${library-section-ids.movies}")
  private Set<Long> librarySectionIds;

  @Autowired
  public MigrationServiceImpl(final MetadataItemRepository metadataItemRepository,
      final MovieMapper movieMapper,
      final MoviesRepository moviesRepository,
      @Qualifier("secondaryDbEntityManager") final EntityManager secondaryDbEntityManager) {
    this.metadataItemRepository = metadataItemRepository;
    this.movieMapper = movieMapper;
    this.moviesRepository = moviesRepository;
    this.secondaryDbEntityManager = secondaryDbEntityManager;
    movies = Collections.synchronizedSet(new HashSet<>());
  }

  @Override
  @Transactional
  public void migrateMoviesFromSqliteToMySql() {
    log.info("Start of migration");
    metadataItemRepository.findAllByMetadataTypeAndLibrarySectionIdIn(MetadataType.MOVIE, librarySectionIds)
        .distinct()
        .forEach(metadataItem -> {
          addMovie(movieMapper.metadataItemToMovie(metadataItem), movies);
          secondaryDbEntityManager.detach(metadataItem);
        });
    synchronized (this.movies) {
      if (!movies.isEmpty()) {
        saveAndClear();
      }
    }
    log.info("End of migration");
  }

  private void addMovie(final Movie movie, final Set<Movie> movies) {
    final Movie movieToSave = moviesRepository.findByTitleAndYear(movie.getTitle(), movie.getYear())
        .map(existingMovie -> movieMapper.updateMovie(movie, existingMovie))
        .orElse(movie);
    movies.add(movieToSave);
    synchronized (this.movies) {
      if (movies.size() == BATCH_SIZE) {
        saveAndClear();
      }
    }
  }

  private void saveAndClear() {
    moviesRepository.saveAll(movies);
    movies.clear();
  }
}
