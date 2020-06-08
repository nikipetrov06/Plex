package com.scalefocus.java.plexnikolaypetrov.entities.primary;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@EqualsAndHashCode(of = {"title", "year"})
public abstract class MediaCharacteristics {

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  private String id;

  @Column(name = "title")
  private String title;

  @Column(name = "description")
  private String description;

  @Column(name = "rating")
  private Double rating;

  @Column(name = "release_date")
  private LocalDate releaseDate;

  @Column(name = "director")
  private String director;

  @Column(name = "writer")
  private String writer;

  @Column(name = "stars")
  private String stars;

  @Column(name = "duration")
  private Integer duration;

  @Column(name = "imdb_id")
  private String imdbId;

  @Column(name = "year")
  private Integer year;

  @Column(name = "genre")
  private String genre;

  @Column(name = "audio")
  private String audio;

  @Column(name = "subtitles")
  private String subtitles;
}
