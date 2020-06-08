package com.scalefocus.java.plexnikolaypetrov.repositories.secondary;

import com.scalefocus.java.plexnikolaypetrov.entities.secondary.MetadataItem;
import com.scalefocus.java.plexnikolaypetrov.enums.MetadataType;
import java.util.Set;
import java.util.stream.Stream;
import javax.persistence.QueryHint;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import static org.hibernate.annotations.QueryHints.READ_ONLY;
import static org.hibernate.jpa.QueryHints.HINT_CACHEABLE;
import static org.hibernate.jpa.QueryHints.HINT_FETCH_SIZE;

/**
 * Definition for MetadataItemRepository methods
 */
@Repository
public interface MetadataItemRepository extends CrudRepository<MetadataItem, Long> {

  @QueryHints(value = {
      @QueryHint(name = HINT_FETCH_SIZE, value = "20"),
      @QueryHint(name = HINT_CACHEABLE, value = "false"),
      @QueryHint(name = READ_ONLY, value = "true")
  })
  Stream<MetadataItem> findAllByMetadataTypeAndLibrarySectionIdIn(final MetadataType metadataType, final Set<Long> librarySectionId);
}
