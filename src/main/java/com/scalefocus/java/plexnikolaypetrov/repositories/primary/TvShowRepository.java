package com.scalefocus.java.plexnikolaypetrov.repositories.primary;

import com.scalefocus.java.plexnikolaypetrov.entities.primary.TvShow;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for TvShow Entity
 */
@Repository
public interface TvShowRepository extends CrudRepository<TvShow, String> {
}
