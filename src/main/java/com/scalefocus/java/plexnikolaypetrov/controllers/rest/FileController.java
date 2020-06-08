package com.scalefocus.java.plexnikolaypetrov.controllers.rest;

import com.scalefocus.java.plexnikolaypetrov.exceptions.FileException;
import com.scalefocus.java.plexnikolaypetrov.services.primary.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
public class FileController {

  private final FileService fileService;

  @PostMapping("/upload")
  @ResponseStatus(HttpStatus.CREATED)
  public void upload(@RequestPart("file") final MultipartFile file) throws FileException {
    fileService.upload(file);
  }
}
