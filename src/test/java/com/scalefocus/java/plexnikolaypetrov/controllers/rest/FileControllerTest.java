package com.scalefocus.java.plexnikolaypetrov.controllers.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scalefocus.java.plexnikolaypetrov.services.primary.impl.FileServiceImpl;
import java.nio.charset.StandardCharsets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(MockitoJUnitRunner.class)
public class FileControllerTest {

  @Spy
  @InjectMocks
  private FileController fileController;

  @Mock
  private FileServiceImpl fileService;

  private MockMvc mockMvc;

  private ObjectMapper objectMapper;

  @Before
  public void init() {
    mockMvc = standaloneSetup(fileController).build();
    objectMapper = new ObjectMapper();
  }

  @Test
  public void test_upload() throws Exception {
    final MockMultipartFile file =
        new MockMultipartFile("file",
            "file.db",
            "application/vnd.sqlite3",
            "SQLite format 3".getBytes(StandardCharsets.UTF_8));

   mockMvc.perform(multipart("/upload")
       .file(file)
       .contentType(MediaType.APPLICATION_JSON))
       .andExpect(status().isCreated());
  }
}
