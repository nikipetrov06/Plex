package com.scalefocus.java.plexnikolaypetrov.services;

import com.scalefocus.java.plexnikolaypetrov.exceptions.FileException;
import com.scalefocus.java.plexnikolaypetrov.services.primary.impl.FileServiceImpl;
import com.scalefocus.java.plexnikolaypetrov.services.primary.impl.MigrationServiceImpl;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;


import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FileServiceImplTest {

  @InjectMocks
  private FileServiceImpl fileService;

  @Mock
  private MigrationServiceImpl migrationService;

  @Rule
  public TemporaryFolder folder = new TemporaryFolder();

  @Before
  public void init() {
    ReflectionTestUtils.setField(fileService, "filePath", folder.getRoot().getAbsolutePath());
    ReflectionTestUtils.setField(fileService, "extensions", new String[] {"db", "sqlite", "sqlitedb"});
    ReflectionTestUtils.setField(fileService, "sqliteFormat", "SQLite format 3");
  }

  @Test
  public void test_upload_whenFileIsNull() {
    //Given
    final MultipartFile file = null;

    //When
    final Exception exception = assertThrows(FileException.class, () -> fileService.upload(file));

    //Then
    final String expected = "Wrong file given";
    final String actual = exception.getMessage();
    assertThat(actual, is(expected));
  }

  @Test
  public void test_upload_whenInvalidFile() {
    //Given
    final MultipartFile file =
        new MockMultipartFile("file",
            "file.txt",
            "text/plain",
            "Some content".getBytes(StandardCharsets.UTF_8));

    //When
    final Exception exception = assertThrows(FileException.class, () -> fileService.upload(file));

    //Then
    final String expected = "Wrong file given";
    final String actual = exception.getMessage();
    assertThat(actual, is(expected));
  }

  @Test
  public void test_upload_whenfileIsValid() throws FileException {
    //Given
    final MultipartFile file =
        new MockMultipartFile("file",
            "file.db",
            "application/vnd.sqlite3",
            "SQLite format 3".getBytes(StandardCharsets.UTF_8));

    //When
    fileService.upload(file);

    //Then
    assertThat(folder.getRoot().toPath().endsWith("file.db"));
  }

  @Test
  public void test_upload_whenIOExceptionThrown() throws IOException {
    //Given
    final MultipartFile file = mock(MultipartFile.class);
    when(file.getOriginalFilename()).thenReturn("file.db");
    when(file.getInputStream()).thenThrow(new IOException());

    //When
    final Exception exception = assertThrows(FileException.class, () -> fileService.upload(file));

    //Then
    final String expected = "Something went wrong with file";
    final String actual = exception.getMessage();
    assertThat(actual, is(expected));
  }
}
