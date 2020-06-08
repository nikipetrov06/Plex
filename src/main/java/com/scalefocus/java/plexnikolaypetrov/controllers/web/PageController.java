package com.scalefocus.java.plexnikolaypetrov.controllers.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

  @GetMapping("/upload-page")
  public String getUploadPage() {
    return "uploadPage";
  }
}
