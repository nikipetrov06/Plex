package com.scalefocus.java.plexnikolaypetrov.utils;

import com.scalefocus.java.plexnikolaypetrov.entities.secondary.MediaItem;
import com.scalefocus.java.plexnikolaypetrov.entities.secondary.MediaStream;
import com.scalefocus.java.plexnikolaypetrov.entities.secondary.MetadataItem;
import com.scalefocus.java.plexnikolaypetrov.enums.StreamType;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class MetadataItemUtil {

  private static final char FIRST_SYMBOL_TO_LOOK_FOR = '/';
  private static final char LAST_SYMBOL_TO_LOOK_FOR = '?';
  private static final int LENGTH_OF_IMDB_ID = 9;
  private static final String IMDB_KEYWORD_FOR_LINK = "imdb";
  private static final String KEY_LETTERS_THAT_IMDB_ID_HAS_TO_HAVE = "tt";
  private static final int PLACES_ADDED_TO_CORRECT_THE_POSITION = 1;
  private static final int FIRST_ELEMENT = 0;

  public String getMediaStreamsWithStreamType(final MetadataItem metadataItem, final StreamType streamType) {
    final String languages = metadataItem.getMediaItems().stream()
        .map(MediaItem::getMediaStreamList)
        .flatMap(List::stream)
        .filter(stream -> stream.getStreamTypeId() == streamType)
        .map(MediaStream::getLanguage)
        .filter(StringUtils::isNotBlank)
        .distinct()
        .collect(Collectors.joining(","));

    return StringUtils.isBlank(languages) ? null : languages;
  }

  public String extractImdbId(final String guid) {
    if (!guid.contains(IMDB_KEYWORD_FOR_LINK)) {
      return null;
    }
    final String imdbId = guid.substring(guid.lastIndexOf(FIRST_SYMBOL_TO_LOOK_FOR) + PLACES_ADDED_TO_CORRECT_THE_POSITION,
        guid.indexOf(LAST_SYMBOL_TO_LOOK_FOR));
    if (imdbId.startsWith(KEY_LETTERS_THAT_IMDB_ID_HAS_TO_HAVE) && imdbId.length() == LENGTH_OF_IMDB_ID) {
      return imdbId;
    }
    return null;
  }

  public LocalDate convertReleaseDateToLocalDate(final String releaseDate) {
    if (StringUtils.isBlank(releaseDate)) {
      return null;
    }
    return LocalDate.parse(releaseDate.split(StringUtils.SPACE)[FIRST_ELEMENT]);
  }
}
