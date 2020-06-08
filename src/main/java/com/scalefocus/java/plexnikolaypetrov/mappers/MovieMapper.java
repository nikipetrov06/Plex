package com.scalefocus.java.plexnikolaypetrov.mappers;

import com.scalefocus.java.plexnikolaypetrov.entities.primary.Movie;
import com.scalefocus.java.plexnikolaypetrov.entities.secondary.MetadataItem;
import com.scalefocus.java.plexnikolaypetrov.enums.StreamType;
import com.scalefocus.java.plexnikolaypetrov.utils.MetadataItemUtil;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Definition of MovieMapper
 */
@Mapper(componentModel = "spring", imports = {StreamType.class})
public abstract class MovieMapper {

  @Autowired
  protected MetadataItemUtil metadataItemUtil;

  @Mapping(target = "releaseDate", expression = "java(metadataItemUtil.convertReleaseDateToLocalDate(metadataItem.getReleaseDate()))")
  @Mapping(target = "audio", expression = "java(metadataItemUtil.getMediaStreamsWithStreamType(metadataItem, StreamType.AUDIO))")
  @Mapping(target = "subtitles", expression = "java(metadataItemUtil.getMediaStreamsWithStreamType(metadataItem, StreamType.SUBTITLE))")
  @Mapping(target = "imdbId", expression = "java(metadataItemUtil.extractImdbId(metadataItem.getGuid()))")
  @Mapping(source = "summary", target = "description")
  public abstract Movie metadataItemToMovie(MetadataItem metadataItem);

  @Mapping(target = "id", ignore = true)
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
      nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
  public abstract Movie updateMovie(Movie newMovie, @MappingTarget Movie existingMovie);
}
