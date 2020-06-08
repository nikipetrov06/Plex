package com.scalefocus.java.plexnikolaypetrov.repositories.primary;

import com.scalefocus.java.plexnikolaypetrov.entities.primary.MovieIssue;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository for MovieIssue
 */
public interface MovieIssueRepository extends CrudRepository<MovieIssue, String> {
}
