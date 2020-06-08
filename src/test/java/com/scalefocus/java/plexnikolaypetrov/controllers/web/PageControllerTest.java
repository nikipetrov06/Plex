package com.scalefocus.java.plexnikolaypetrov.controllers.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(MockitoJUnitRunner.class)
public class PageControllerTest {

  @InjectMocks
  private PageController pageController;

  private MockMvc mockMvc;

  @Before
  public void init() {
    mockMvc = standaloneSetup(pageController).build();
  }

  @Test
  public void test_callingUploadPage() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders.get("/upload-page"))
        .andExpect(status().isOk())
        .andExpect(view().name("uploadPage"));
  }
}
