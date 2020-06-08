package com.scalefocus.java.plexnikolaypetrov.utils;

import com.scalefocus.java.plexnikolaypetrov.entities.secondary.MediaItem;
import com.scalefocus.java.plexnikolaypetrov.entities.secondary.MediaStream;
import com.scalefocus.java.plexnikolaypetrov.entities.secondary.MetadataItem;
import com.scalefocus.java.plexnikolaypetrov.enums.StreamType;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;


import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MetadatataItemUtilTest {

  @InjectMocks
  private MetadataItemUtil metadataItemUtil;

  @Test
  public void test_extractImdbId_when_guidDoesNotHaveImdb() {
    //Given
    final String guid = "test";

    //When
    final String resutl = metadataItemUtil.extractImdbId(guid);

    //Then
    assertThat(resutl, is(nullValue()));
  }

  @Test
  public void test_exctractImdbId_when_guidDoesHaveImdbButIdIsInvalid() {
    //Given
    final String guid = "com.plexapp.agents.imdb://2012011?lang=en";

    //When
    final String result = metadataItemUtil.extractImdbId(guid);

    //Then
    assertThat(result, is(nullValue()));
  }

  @Test
  public void test_extractImdbId_when_guidIsValid() {
    //Given
    final String guid = "com.plexapp.agents.imdb://tt2012011?lang=en";

    //When
    final String result = metadataItemUtil.extractImdbId(guid);

    //Then
    assertThat(result, is("tt2012011"));
  }

  @Test
  public void test_getMedaiStreamsWithStreamType() {
    //Given
    final MetadataItem metadataItem = mock(MetadataItem.class);
    final MediaItem mediaItem = mock(MediaItem.class);
    final MediaStream subtitleStream = mock(MediaStream.class);
    final MediaStream audioStream = mock(MediaStream.class);

    when(metadataItem.getMediaItems()).thenReturn(Collections.singletonList(mediaItem));
    when(mediaItem.getMediaStreamList()).thenReturn(Arrays.asList(subtitleStream, audioStream));
    mockMediaStream(subtitleStream, StreamType.SUBTITLE);
    mockMediaStream(audioStream, StreamType.AUDIO);

    //When
    final String audioResult = metadataItemUtil.getMediaStreamsWithStreamType(metadataItem, StreamType.AUDIO);
    final String subtitleResult = metadataItemUtil.getMediaStreamsWithStreamType(metadataItem, StreamType.SUBTITLE);

    //Then
    assertThat(audioResult, is("eng"));
    assertThat(subtitleResult, is("eng"));
  }

  @Test
  public void test_getMediaStreamsWithStreamType_when_streamIsEmpty() {
    //Given
    final MetadataItem metadataItem = mock(MetadataItem.class);
    final MediaItem mediaItem = mock(MediaItem.class);
    when(metadataItem.getMediaItems()).thenReturn(Collections.singletonList(mediaItem));

    //When
    String result = metadataItemUtil.getMediaStreamsWithStreamType(metadataItem, StreamType.SUBTITLE);

    //Then
    assertThat(result, is(nullValue()));
  }

  private void mockMediaStream(final MediaStream mediaStream, final StreamType streamType) {
    when(mediaStream.getStreamTypeId()).thenReturn(streamType);
    when(mediaStream.getLanguage()).thenReturn("eng");
  }

  @Test
  public void test_convertReleaseDateToLocalDate_whenReleaseDateIsNull() {
    //Given
    final String releaseDate = null;

    //When
    final LocalDate result = metadataItemUtil.convertReleaseDateToLocalDate(releaseDate);

    //Then
    assertThat(result, is(nullValue()));
  }

  @Test
  public void test_convertReleaseDateToLocalDate_whenReleaseDateIsBlank() {
    //Given
    final String releaseDate = "";

    //When
    final LocalDate result = metadataItemUtil.convertReleaseDateToLocalDate(releaseDate);

    //Then
    assertThat(result, is(nullValue()));
  }

  @Test
  public void test_convertReleaseDateToLocalDate() {
    //Given
    final String releaseDate = "2020-12-12 01:00:00";

    //When
    final LocalDate actual = metadataItemUtil.convertReleaseDateToLocalDate(releaseDate);

    //Then
    final LocalDate expected = LocalDate.of(2020,12,12);
    assertThat(actual, is(expected));
  }
}
