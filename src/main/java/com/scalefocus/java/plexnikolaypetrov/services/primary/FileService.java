package com.scalefocus.java.plexnikolaypetrov.services.primary;

import com.scalefocus.java.plexnikolaypetrov.exceptions.FileException;
import org.springframework.web.multipart.MultipartFile;

/**
 * Definition of FileService methods
 */
public interface FileService {

  void upload(MultipartFile file) throws FileException;
}
