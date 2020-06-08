package com.scalefocus.java.plexnikolaypetrov.utils.constants;

public final class ValidationConstants {

  private ValidationConstants() {
    throw new AssertionError();
  }

  public static final String EMAIL = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
  public static final String PASSWORD = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
}
