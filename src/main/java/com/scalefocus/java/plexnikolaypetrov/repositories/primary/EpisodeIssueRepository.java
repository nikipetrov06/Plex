package com.scalefocus.java.plexnikolaypetrov.repositories.primary;

import com.scalefocus.java.plexnikolaypetrov.entities.primary.EpisodeIssue;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository for Episode Issue
 */
public interface EpisodeIssueRepository extends CrudRepository<EpisodeIssue, String> {
}
