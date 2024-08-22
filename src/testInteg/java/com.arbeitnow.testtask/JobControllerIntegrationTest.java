package com.arbeitnow.testtask;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = "/db/insert-jobs.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
class JobControllerIntegrationTest {

  private static final String BASE_URL = "/jobs";
  private static final String TOP_TEN_JOBS_URI = "/top-ten";
  private static final String LOCATIONS_STATISTICS_URI = "/locations-statistics";
  private static final int ELEMENTS_IN_PAGE = 11;
  private static final int ELEMENTS_IN_CONTENT = 2;
  private static final int TOTAL_PAGES = 8;
  private static final int TOTAL_ELEMENTS = 15;
  private static final int ELEMENTS_IN_JOB = 10;
  private static final int PAGE_NUMBER = 0;
  private static final int LOCATIONS_NUMBER = 10;
  private static final int JOBS_IN_NY = 3;
  private static final int JOBS_IN_SD = 1;

  @Autowired private MockMvc mockMvc;

  @Test
  void getAllJobs_shouldReturnOk_whenDataIsPresent() throws Exception {
    mockMvc
        .perform(get(BASE_URL).param("page", "0").param("size", "2").param("sort", "title"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()", is(ELEMENTS_IN_PAGE)))
        .andExpect(jsonPath("$.totalPages", is(TOTAL_PAGES)))
        .andExpect(jsonPath("$.totalElements", is(TOTAL_ELEMENTS)))
        .andExpect(jsonPath("$.size", is(ELEMENTS_IN_CONTENT)))
        .andExpect(jsonPath("$.content.length()", is(ELEMENTS_IN_CONTENT)))
        .andExpect(jsonPath("$.content.[0].length()", is(ELEMENTS_IN_JOB)))
        .andExpect(jsonPath("$.content.[0].title", is("Business Analyst")))
        .andExpect(jsonPath("$.content.[1].title", is("Cyber Security Specialist")))
        .andExpect(jsonPath("$.number", is(PAGE_NUMBER)))
        .andExpect(jsonPath("$.sort.sorted", is(true)))
        .andExpect(jsonPath("$.numberOfElements", is(ELEMENTS_IN_CONTENT)))
        .andExpect(jsonPath("$.first", is(true)))
        .andExpect(jsonPath("$.last", is(false)))
        .andExpect(jsonPath("$.empty", is(false)));
  }

  @Test
  void getTopTen_shouldReturnOk_whenDataIsPresent() throws Exception {
    mockMvc
        .perform(get(BASE_URL + TOP_TEN_JOBS_URI))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()", is(10)))
        .andExpect(jsonPath("$.[0].length()", is(ELEMENTS_IN_JOB)))
        .andExpect(jsonPath("$.[0].title", is("Cyber Security Specialist")))
        .andExpect(jsonPath("$.[9].title", is("Network Administrator")));
  }

  @Test
  void getStatisticsForLocation_shouldReturnOk_whenDataIsPresent() throws Exception {
    mockMvc
        .perform(get(BASE_URL + LOCATIONS_STATISTICS_URI))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()", is(LOCATIONS_NUMBER)))
        .andExpect(jsonPath("$.['New York, NY']", is(JOBS_IN_NY)))
        .andExpect(jsonPath("$.['San Diego, CA']", is(JOBS_IN_SD)));
  }
}
