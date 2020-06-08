package com.scalefocus.java.plexnikolaypetrov.entities.primary;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "movies_issues")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieIssue extends IssuesCharacteristics {

  @OneToOne
  @JoinColumn(name = "movie_id")
  @MapsId
  private Movie movie;
}
