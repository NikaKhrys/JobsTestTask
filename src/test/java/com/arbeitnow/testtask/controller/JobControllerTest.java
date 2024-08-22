package com.arbeitnow.testtask.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.arbeitnow.testtask.TestDataGenerator;
import com.arbeitnow.testtask.service.JobService;
import com.arbeitnow.testtask.service.dto.JobDto;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(JobController.class)
class JobControllerTest {
  private static final Pageable DEFAULT_PAGEABLE = PageRequest.of(0, 2, Sort.by("title"));
  private static final String BASE_URL = "/jobs";
  private static final String TOP_TEN_JOBS_URI = "/top-ten";
  private static final String LOCATIONS_STATISTICS_URI = "/locations-statistics";
  private static final int ELEMENTS_IN_PAGE = 11;
  private static final int ELEMENTS_IN_CONTENT = 2;
  private static final int TOTAL_PAGES = 5;
  private static final int TOTAL_ELEMENTS = 10;
  private static final int ELEMENTS_IN_JOB = 10;
  private static final int LOCATIONS_NUMBER = 4;
  private static final int JOBS_IN_NY = 5;

  @Autowired private MockMvc mockMvc;
  @MockBean private JobService jobService;

  @Test
  void getAllJobs_shouldReturnOk_whenDataIsPresent() throws Exception {
    Page<JobDto> jobsDto = TestDataGenerator.getJobsDtoPage();
    when(jobService.getAll(DEFAULT_PAGEABLE)).thenReturn(jobsDto);

    mockMvc
        .perform(get(BASE_URL).param("page", "0").param("size", "2").param("sort", "title"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()", is(ELEMENTS_IN_PAGE)))
        .andExpect(jsonPath("$.totalPages", is(TOTAL_PAGES)))
        .andExpect(jsonPath("$.totalElements", is(TOTAL_ELEMENTS)))
        .andExpect(jsonPath("$.size", is(DEFAULT_PAGEABLE.getPageSize())))
        .andExpect(jsonPath("$.content.length()", is(ELEMENTS_IN_CONTENT)))
        .andExpect(jsonPath("$.content.[0].length()", is(ELEMENTS_IN_JOB)))
        .andExpect(jsonPath("$.content.[0].title", is("Cyber Security Specialist")))
        .andExpect(jsonPath("$.content.[1].title", is("Software Developer")))
        .andExpect(jsonPath("$.number", is(DEFAULT_PAGEABLE.getPageNumber())))
        .andExpect(jsonPath("$.sort.sorted", is(true)))
        .andExpect(jsonPath("$.numberOfElements", is(DEFAULT_PAGEABLE.getPageSize())))
        .andExpect(jsonPath("$.first", is(true)))
        .andExpect(jsonPath("$.last", is(false)))
        .andExpect(jsonPath("$.empty", is(false)));

    verify(jobService, times(1)).getAll(DEFAULT_PAGEABLE);
  }

  @Test
  void getAllJobs_shouldReturnNoContent_whenDataIsNotPresent() throws Exception {
    Page<JobDto> emptyJobsPage = Page.empty();
    when(jobService.getAll(DEFAULT_PAGEABLE)).thenReturn(emptyJobsPage);

    mockMvc
        .perform(get(BASE_URL).param("page", "0").param("size", "2").param("sort", "title"))
        .andExpect(status().isNoContent());
  }

  @Test
  void getTopTen_shouldReturnOk_whenDataIsPresent() throws Exception {
    List<JobDto> tenJobs = TestDataGenerator.getTenJobsDto();
    when(jobService.getTopTen()).thenReturn(tenJobs);

    mockMvc
        .perform(get(BASE_URL + TOP_TEN_JOBS_URI))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()", is(tenJobs.size())))
        .andExpect(jsonPath("$.[0].length()", is(ELEMENTS_IN_JOB)))
        .andExpect(jsonPath("$.[0].title", is("Cyber Security Specialist")));

    verify(jobService, times(1)).getTopTen();
  }

  @Test
  void getTopTen_shouldReturnNoContent_whenDataIsNotPresent() throws Exception {
    List<JobDto> emptyJobsList = List.of();
    when(jobService.getTopTen()).thenReturn(emptyJobsList);

    mockMvc.perform(get(BASE_URL + TOP_TEN_JOBS_URI)).andExpect(status().isNoContent());

    verify(jobService, times(1)).getTopTen();
  }

  @Test
  void getStatisticsForLocations_shouldReturnOk_whenDataIsPresent() throws Exception {
    Map<String, Long> statisticsMap = TestDataGenerator.getStatisticsMap();
    when(jobService.getStatisticsForLocations()).thenReturn(statisticsMap);

    mockMvc
        .perform(get(BASE_URL + LOCATIONS_STATISTICS_URI))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()", is(LOCATIONS_NUMBER)))
        .andExpect(jsonPath("$.NY", is(JOBS_IN_NY)));

    verify(jobService, times(1)).getStatisticsForLocations();
  }

  @Test
  void getStatisticsForLocations_shouldReturnNoContent_whenDataIsNotPresent() throws Exception {
    Map<String, Long> emptyStatisticsMap = Map.of();
    when(jobService.getStatisticsForLocations()).thenReturn(emptyStatisticsMap);

    mockMvc.perform(get(BASE_URL + LOCATIONS_STATISTICS_URI)).andExpect(status().isNoContent());
  }
}
