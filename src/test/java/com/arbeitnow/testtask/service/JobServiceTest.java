package com.arbeitnow.testtask.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.arbeitnow.testtask.TestDataGenerator;
import com.arbeitnow.testtask.mappers.JobMapper;
import com.arbeitnow.testtask.repository.JobRepository;
import com.arbeitnow.testtask.repository.entity.Job;
import com.arbeitnow.testtask.service.dto.JobDto;
import com.arbeitnow.testtask.service.impls.JobServiceImpl;
import jakarta.persistence.Tuple;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class JobServiceTest {

  private static final Pageable DEFAULT_PAGEABLE = PageRequest.of(0, 2, Sort.by("title"));

  @InjectMocks private JobServiceImpl jobService;
  @Mock private JobRepository jobRepository;

  @BeforeEach
  void mapperSetup() {
    JobMapper jobMapper = Mappers.getMapper(JobMapper.class);
    ReflectionTestUtils.setField(jobService, "jobMapper", jobMapper);
  }

  @Test
  void getAll_shouldReturnJobsPage_whenDataIsPresent() {
    Page<Job> jobsEntity = TestDataGenerator.getJobsEntityPage();
    Page<JobDto> jobsDto = TestDataGenerator.getJobsDtoPage();

    when(jobRepository.findAll(DEFAULT_PAGEABLE)).thenReturn(jobsEntity);

    Page<JobDto> foundJobs = jobService.getAll(DEFAULT_PAGEABLE);
    assertEquals(jobsDto, foundJobs);
    verify(jobRepository, times(1)).findAll(DEFAULT_PAGEABLE);
  }

  @Test
  void getAll_shouldReturnEmptyPage_whenDataIsNotPresent() {
    Page<Job> emptyJobsPage = Page.empty();
    when(jobRepository.findAll(DEFAULT_PAGEABLE)).thenReturn(emptyJobsPage);

    Page<JobDto> foundJobs = jobService.getAll(DEFAULT_PAGEABLE);
    assertTrue(foundJobs.isEmpty());
    verify(jobRepository, times(1)).findAll(DEFAULT_PAGEABLE);
  }

  @Test
  void getTopTen_shouldReturnJobsList_whenDataIsPresent() {
    List<Job> tenJobsEntity = TestDataGenerator.getTenJobsEntity();
    List<JobDto> tenJobsDto = TestDataGenerator.getTenJobsDto();

    when(jobRepository.findTopTen()).thenReturn(tenJobsEntity);

    List<JobDto> foundJobs = jobService.getTopTen();
    assertEquals(tenJobsDto, foundJobs);
    verify(jobRepository, times(1)).findTopTen();
  }

  @Test
  void getTopTen_shouldReturnEmptyList_whenDataIsNotPresent() {
    List<Job> emptyJobsList = List.of();
    when(jobRepository.findTopTen()).thenReturn(emptyJobsList);

    List<JobDto> foundJobs = jobService.getTopTen();
    assertTrue(foundJobs.isEmpty());
    verify(jobRepository, times(1)).findTopTen();
  }

  @Test
  void getStatisticsForLocations_shouldReturnStatisticsMap_whenDataIsPresent() {
    Tuple tuple1 = mock(Tuple.class);
    Tuple tuple2 = mock(Tuple.class);
    List<Tuple> statistics = List.of(tuple1, tuple2);

    when(jobRepository.countJobsByLocation()).thenReturn(statistics);
    when(tuple1.get("location", String.class)).thenReturn("NY");
    when(tuple1.get("jobs_count", Long.class)).thenReturn(3L);
    when(tuple2.get("location", String.class)).thenReturn("TX");
    when(tuple2.get("jobs_count", Long.class)).thenReturn(2L);

    Map<String, Long> foundStatistics = jobService.getStatisticsForLocations();
    assertEquals(statistics.size(), foundStatistics.size());
    assertEquals(3, foundStatistics.get("NY"));
    assertEquals(2, foundStatistics.get("TX"));
    verify(jobRepository, times(1)).countJobsByLocation();
  }

  @Test
  void getStatisticsForLocation_shouldReturnEmptyMap_whenDataIsNotPresent() {
    List<Tuple> emptyStatistics = List.of();
    when(jobRepository.countJobsByLocation()).thenReturn(emptyStatistics);

    Map<String, Long> foundStatistics = jobService.getStatisticsForLocations();
    assertTrue(foundStatistics.isEmpty());
    verify(jobRepository, times(1)).countJobsByLocation();
  }
}
