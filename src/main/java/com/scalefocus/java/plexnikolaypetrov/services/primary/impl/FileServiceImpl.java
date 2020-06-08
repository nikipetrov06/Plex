package com.scalefocus.java.plexnikolaypetrov.services.primary.impl;

import com.scalefocus.java.plexnikolaypetrov.exceptions.FileException;
import com.scalefocus.java.plexnikolaypetrov.services.primary.FileService;
import com.scalefocus.java.plexnikolaypetrov.services.primary.MigrationService;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import static org.apache.commons.io.FilenameUtils.getExtension;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

  private final MigrationService migrationService;

  public static final int START_FROM = 0;
  public static final int FILE_SIGNATURE_SIZE = 15;

  @Value("${sqlite.format}")
  public String sqliteFormat;

  @Value("${file.path}")
  private String filePath;

  @Value("${sqlite.extensions}")
  private String[] extensions;

  @Override
  public void upload(final MultipartFile file) throws FileException {
    try {
      if (Objects.isNull(file) || !isValidSqlite(file)) {
        throw new FileException("Wrong file given");
      }
      log.info("Start of uploading file, file {}", file.getOriginalFilename());
      final String filename = String.format("%s_%s",  LocalDateTime.now(), file.getOriginalFilename());
      final Path copyLocation = Paths.get(String.format("%s%s%s", filePath, File.separator, filename));
      Files.copy(file.getInputStream(), copyLocation);
      log.info("File finished uploading successfully, file {}", filename);
      migrationService.migrateMoviesFromSqliteToMySql();
    } catch (IOException exception) {
      throw new FileException("Something went wrong with file", exception);
    }
  }

  private boolean isValidSqlite(final MultipartFile file) throws IOException {
    final Set<String> extensionsSet = Stream.of(extensions).collect(Collectors.toSet());
    final String extension = getExtension(file.getOriginalFilename());
    if (!extensionsSet.contains(extension)) {
      return false;
    }
    try (final InputStream inputStream = file.getInputStream()) {
      final byte[] buffer = new byte[FILE_SIGNATURE_SIZE];
      final int readBytes = inputStream.read(buffer, START_FROM, FILE_SIGNATURE_SIZE);
      final String fileSignature = new String(buffer, StandardCharsets.UTF_8);
      return readBytes == FILE_SIGNATURE_SIZE && sqliteFormat.equals(fileSignature);
    }
  }
}
