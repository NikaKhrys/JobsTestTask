package com.arbeitnow.testtask.controller;

import com.arbeitnow.testtask.service.JobService;
import com.arbeitnow.testtask.service.dto.JobDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

/**
 * RestController that supports endpoints to manage jobs
 *
 * @author NikaKhrys
 */
@RestController
@RequestMapping("/jobs")
@RequiredArgsConstructor
public class JobController {

  private final JobService jobService;
  private static final Pageable DEFAULT_PAGEABLE = PageRequest.of(0, 20);

  /**
   * Retrieves all jobs with pagination and sorting
   *
   * @param pageable - number and size of the page, sorting criteria
   * @return page with jobs
   */
  @Operation(summary = "Get all jobs")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Found all jobs",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(
            responseCode = "204",
            description = "No jobs found",
            content = @Content(mediaType = "application/json"))
      })
  @GetMapping
  public ResponseEntity<Page<JobDto>> getAllJobs(@ParameterObject Pageable pageable) {
    if (pageable == null) pageable = DEFAULT_PAGEABLE;
    Page<JobDto> jobDtos = jobService.getAll(pageable);

    if (jobDtos.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    return new ResponseEntity<>(jobDtos, HttpStatus.OK);
  }

  /**
   * Retrieves ten newest jobs
   *
   * @return list with top ten jobs
   */
  @Operation(summary = "Get top ten newest jobs")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Found top ten jobs",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(
            responseCode = "204",
            description = "No jobs found",
            content = @Content(mediaType = "application/json"))
      })
  @GetMapping("/top-ten")
  public ResponseEntity<List<JobDto>> getTopTen() {
    List<JobDto> jobDtos = jobService.getTopTen();
    if (jobDtos.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    return new ResponseEntity<>(jobDtos, HttpStatus.OK);
  }

  /**
   * Retrieves statistics on jons number for each location
   *
   * @return key-value pair with locations and corresponding jobs number in descending order
   */
  @Operation(summary = "Get locations statistics")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Found locations statistics",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(
            responseCode = "204",
            description = "No statistics found",
            content = @Content(mediaType = "application/json"))
      })
  @GetMapping("/locations-statistics")
  public ResponseEntity<Map<String, Long>> getStatisticsForLocations() {
    Map<String, Long> statistics = jobService.getStatisticsForLocations();
    if (statistics.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    return new ResponseEntity<>(statistics, HttpStatus.OK);
  }
}
