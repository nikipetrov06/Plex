package com.scalefocus.java.plexnikolaypetrov.entities.primary;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "episodes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Episode extends MediaCharacteristics {

  @Column(name = "number")
  private Integer number;

  @ManyToOne
  @JoinColumn(name = "seasons_id")
  private Season season;

  @OneToOne(mappedBy = "episode", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY, orphanRemoval = true)
  private EpisodeIssue episodeIssue;
}
