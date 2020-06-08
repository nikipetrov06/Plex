package com.scalefocus.java.plexnikolaypetrov.services;

import com.scalefocus.java.plexnikolaypetrov.entities.primary.Movie;
import com.scalefocus.java.plexnikolaypetrov.entities.secondary.MetadataItem;
import com.scalefocus.java.plexnikolaypetrov.enums.MetadataType;
import com.scalefocus.java.plexnikolaypetrov.mappers.MovieMapper;
import com.scalefocus.java.plexnikolaypetrov.repositories.primary.MoviesRepository;
import com.scalefocus.java.plexnikolaypetrov.repositories.secondary.MetadataItemRepository;
import com.scalefocus.java.plexnikolaypetrov.services.primary.impl.MigrationServiceImpl;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.EntityManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MigrationServiceImplTest {

  @InjectMocks
  private MigrationServiceImpl migrationServiceImpl;

  @Mock
  private MetadataItemRepository metadataItemRepository;

  @Mock
  private MovieMapper movieMapper;

  @Mock
  private MoviesRepository moviesRepository;

  @Mock
  private EntityManager secondaryDbEntityManager;

  @Captor
  private ArgumentCaptor<Set<Movie>> movies;

  private Set<Long> movieIds;

  @Before
  public void init() {
    movieIds = Stream.of(5L, 11L, 24L, 25L, 28L, 30L).collect(Collectors.toSet());
    ReflectionTestUtils.setField(migrationServiceImpl, "librarySectionIds", movieIds);
  }

  @Test
  public void test_migrateMoviesFromSqliteToMySql() {
    //Given
    final MetadataItem existing = mock(MetadataItem.class);
    final MetadataItem newMetadataItem = mock(MetadataItem.class);
    final Movie movie = mockMovie("Batman", 2005);
    final Movie newMovie = mockMovie("Star wars", 2006);
    when(metadataItemRepository.findAllByMetadataTypeAndLibrarySectionIdIn(MetadataType.MOVIE, movieIds))
        .thenReturn(Stream.of(existing, newMetadataItem));
    when(moviesRepository.findByTitleAndYear("Batman", 2005)).thenReturn(Optional.of(movie));
    when(moviesRepository.findByTitleAndYear("Star wars", 2006)).thenReturn(Optional.empty());
    metadataItemToMovieMock(existing, movie);
    metadataItemToMovieMock(newMetadataItem, newMovie);

    //When
    migrationServiceImpl.migrateMoviesFromSqliteToMySql();

    //Then
    verify(moviesRepository).saveAll(any());
  }

  private void metadataItemToMovieMock(final MetadataItem existing, final Movie movie) {
    when(movieMapper.metadataItemToMovie(existing)).thenReturn(movie);
  }

  private static Movie mockMovie(final String name, final int year) {
    final Movie movie = mock(Movie.class);
    when(movie.getTitle()).thenReturn(name);
    when(movie.getYear()).thenReturn(year);
    return movie;
  }
}
