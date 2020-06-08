package com.scalefocus.java.plexnikolaypetrov.entities.secondary;

import com.scalefocus.java.plexnikolaypetrov.enums.StreamType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "media_streams")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MediaStream {

  @Id
  private Long id;

  @Column(name = "stream_type_id")
  @Enumerated
  private StreamType streamTypeId;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "media_item_id")
  private MediaItem mediaItem;

  @Column(name = "language")
  private String language;
}
