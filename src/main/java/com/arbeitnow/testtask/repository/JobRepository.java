package com.arbeitnow.testtask.repository;

import com.arbeitnow.testtask.repository.entity.Job;
import jakarta.persistence.Tuple;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface JobRepository
    extends PagingAndSortingRepository<Job, UUID>, CrudRepository<Job, UUID> {

  @Query(value = "SELECT * FROM jobs ORDER BY created_at DESC LIMIT 10", nativeQuery = true)
  List<Job> findTopTen();

  @Query(
      value =
          """
      SELECT location, COUNT(*) AS jobs_count
      FROM jobs
      GROUP BY location
      ORDER BY jobs_count DESC;
      """,
      nativeQuery = true)
  List<Tuple> countJobsByLocation();
}
