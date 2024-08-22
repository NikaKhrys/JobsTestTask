package com.arbeitnow.testtask.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.arbeitnow.testtask.TestDataGenerator;
import com.arbeitnow.testtask.repository.entity.Job;
import jakarta.persistence.Tuple;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@DataJpaTest
@Sql(scripts = "/db/insert-jobs.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
class JobRepositoryTest {

  private static final int UNIQUE_LOCATIONS_NUMBER = 10;
  private static final int JOBS_IN_NY = 3;
  private static final String CLEAN_TABLES_QUERY =
      "DELETE FROM jobs; DELETE FROM jobs_types; DELETE FROM jobs_tags;";

  @Autowired private JobRepository jobRepository;

  @Test
  void findTopTen_shouldReturnListOfTen_whenDataIsPresent() {
    List<Job> topTenJobs = jobRepository.findTopTen();
    Job newstJob = TestDataGenerator.getNewestJobEntity();
    Job oldestJob = TestDataGenerator.getOldestJobEntity();
    assertTrue(topTenJobs.contains(newstJob));
    assertFalse(topTenJobs.contains(oldestJob));
  }

  @Sql(statements = CLEAN_TABLES_QUERY, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
  @Test
  void findTopTen_shouldReturnEmptyList_whenDataIsNotPresent() {
    List<Job> emptyJobsList = jobRepository.findTopTen();
    assertTrue(emptyJobsList.isEmpty());
  }

  @Test
  void countJobsByLocation_shouldReturnJobsCount_whenDataIsPresent() {
    List<Tuple> jobsCountList = jobRepository.countJobsByLocation();
    Map<String, Long> jobsCountMap = new LinkedHashMap<>();
    jobsCountList.stream()
        .forEach(
            item ->
                jobsCountMap.put(
                    item.get("location", String.class), item.get("jobs_count", Long.class)));

    assertEquals(UNIQUE_LOCATIONS_NUMBER, jobsCountMap.size());
    assertEquals(JOBS_IN_NY, jobsCountMap.get("New York, NY"));
  }

  @Sql(statements = CLEAN_TABLES_QUERY, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
  @Test
  void countJobsByLocation_shouldReturnEmptyList_whenDataIsNotPresent() {
    List<Tuple> jobsCountList = jobRepository.countJobsByLocation();
    assertTrue(jobsCountList.isEmpty());
  }
}
