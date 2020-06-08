package com.scalefocus.java.plexnikolaypetrov.entities.secondary;

import com.scalefocus.java.plexnikolaypetrov.enums.MetadataType;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "metadata_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"title", "year"})
public class MetadataItem {

  @Id
  private Long id;

  @Column(name = "library_section_id")
  private Long librarySectionId;

  @ManyToOne
  @JoinColumn(name = "parent_id")
  private MetadataItem parentItem;

  @OneToMany(
      mappedBy = "parentItem",
      fetch = FetchType.EAGER
  )
  private Set<MetadataItem> children;

  @Column(name = "metadata_type")
  @Enumerated
  private MetadataType metadataType;

  @Column(name = "guid")
  private String guid;

  @Column(name = "title")
  private String title;

  @Column(name = "rating")
  private Double rating;

  @Column(name = "summary")
  private String summary;

  @Column(name = "[index]")
  private Integer index;

  @Column(name = "duration")
  private Integer duration;

  @Column(name = "tags_genre")
  private String genres;

  @Column(name = "tags_director")
  private String directors;

  @Column(name = "tags_writer")
  private String writers;

  @Column(name = "tags_star")
  private String stars;

  @Column(name = "originally_available_at")
  private String releaseDate;

  @Column(name = "year")
  private Integer year;

  @OneToMany(
      mappedBy = "metadataItems",
      fetch = FetchType.EAGER
  )
  private List<MediaItem> mediaItems;
}
