package com.scalefocus.java.plexnikolaypetrov.entities.secondary;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "media_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MediaItem {

  @Id
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "metadata_item_id")
  private MetadataItem metadataItems;

  @OneToMany(
      mappedBy = "mediaItem",
      fetch = FetchType.EAGER
  )
  private List<MediaStream> mediaStreamList;

  @Column(name = "duration")
  private Long duration;
}