package com.scalefocus.java.plexnikolaypetrov.entities.primary;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tv_shows")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TvShow extends MediaCharacteristics {

  @OneToMany(mappedBy = "tvShow", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY, orphanRemoval = true)
  private Set<Season> seasons;
}
