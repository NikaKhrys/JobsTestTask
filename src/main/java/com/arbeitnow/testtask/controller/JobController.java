package com.arbeitnow.testtask.controller;

import com.arbeitnow.testtask.service.JobService;
import com.arbeitnow.testtask.service.dto.JobDto;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jobs")
@RequiredArgsConstructor
public class JobController {

  private final JobService jobService;
  private static final Pageable DEFAULT_PAGEABLE = PageRequest.of(0, 20);

  @GetMapping
  public ResponseEntity<Page<JobDto>> getAllJobs(@ParameterObject Pageable pageable) {
    if (pageable == null) pageable = DEFAULT_PAGEABLE;
    Page<JobDto> jobDtos = jobService.getAll(pageable);

    if (jobDtos.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    return new ResponseEntity<>(jobDtos, HttpStatus.OK);
  }

  @GetMapping("/top-ten")
  public ResponseEntity<List<JobDto>> getTopTen() {
    List<JobDto> jobDtos = jobService.getTopTen();
    if (jobDtos.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    return new ResponseEntity<>(jobDtos, HttpStatus.OK);
  }

  @GetMapping("/locations-statistics")
  public ResponseEntity<Map<String, Long>> getStatisticsForLocations() {
    Map<String, Long> statistics = jobService.getStatisticsForLocations();
    if (statistics.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    return new ResponseEntity<>(statistics, HttpStatus.OK);
  }
}
