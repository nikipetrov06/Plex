package com.scalefocus.java.plexnikolaypetrov.entities.primary;

import com.scalefocus.java.plexnikolaypetrov.enums.MediaIssue;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class IssuesCharacteristics {

  @Id
  private String id;

  @Column(name = "audio")
  @Enumerated(value = EnumType.STRING)
  private MediaIssue audioIssue;

  @Column(name = "subtitles")
  @Enumerated(value = EnumType.STRING)
  private MediaIssue subtitlesIssue;

  @Column(name = "imdb_id")
  @Enumerated(value = EnumType.STRING)
  private MediaIssue imdbIdIssue;
}
