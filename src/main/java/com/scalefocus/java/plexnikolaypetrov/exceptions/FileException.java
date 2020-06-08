package com.scalefocus.java.plexnikolaypetrov.exceptions;

public class FileException extends Exception {

  public FileException(final String msg) {
    super(msg);
  }

  public FileException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
