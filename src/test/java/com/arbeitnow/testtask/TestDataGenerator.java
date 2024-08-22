package com.arbeitnow.testtask;

import com.arbeitnow.testtask.repository.entity.Job;
import com.arbeitnow.testtask.service.dto.JobDto;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class TestDataGenerator {

  private static final Pageable DEFAULT_PAGEABLE = PageRequest.of(0, 2, Sort.by("title"));
  private static final int DEFAULT_TOTAL = 10;

  public static Job getNewestJobEntity() {
    return Job.builder()
        .id(UUID.fromString("9b3f4a6c-7d8e-4f1c-9b2a-5e7f6d9a3c2f"))
        .slug("cyber-security-specialist")
        .companyName("SecureNet")
        .title("Cyber Security Specialist")
        .description("Ensure network security.")
        .remote(false)
        .url("https://securenet.com/job15")
        .location("Seattle, WA")
        .createdAt(BigInteger.valueOf(1692787215L))
        .jobTypes(Set.of("Full-Time"))
        .tags(Set.of("Security", "Cybersecurity"))
        .build();
  }

  public static JobDto getNewestJobDto() {
    return JobDto.builder()
        .id(UUID.fromString("9b3f4a6c-7d8e-4f1c-9b2a-5e7f6d9a3c2f"))
        .slug("cyber-security-specialist")
        .companyName("SecureNet")
        .title("Cyber Security Specialist")
        .description("Ensure network security.")
        .remote(false)
        .url("https://securenet.com/job15")
        .location("Seattle, WA")
        .createdAt(BigInteger.valueOf(1692787215L))
        .jobTypes(Set.of("Full-Time"))
        .tags(Set.of("Security", "Cybersecurity"))
        .build();
  }

  public static Job getOldestJobEntity() {
    return Job.builder()
        .id(UUID.fromString("e4e1a6d7-7e1d-4b9b-aabe-3a8b0a5e59a1"))
        .slug("software-developer")
        .companyName("TechCorp")
        .title("Software Developer")
        .description("Develop and maintain software.")
        .remote(true)
        .url("https://techcorp.com/job1")
        .location("New York, NY")
        .createdAt(BigInteger.valueOf(1692787201L))
        .jobTypes(Set.of("Full-Time", "Remote"))
        .tags(Set.of("Software", "Development"))
        .build();
  }

  public static JobDto getOldestJobDto() {
    return JobDto.builder()
        .id(UUID.fromString("e4e1a6d7-7e1d-4b9b-aabe-3a8b0a5e59a1"))
        .slug("software-developer")
        .companyName("TechCorp")
        .title("Software Developer")
        .description("Develop and maintain software.")
        .remote(true)
        .url("https://techcorp.com/job1")
        .location("New York, NY")
        .createdAt(BigInteger.valueOf(1692787201L))
        .jobTypes(Set.of("Full-Time", "Remote"))
        .tags(Set.of("Software", "Development"))
        .build();
  }

  public static Page<Job> getJobsEntityPage() {
    List<Job> jobs = List.of(getNewestJobEntity(), getOldestJobEntity());
    return new PageImpl<>(jobs, DEFAULT_PAGEABLE, DEFAULT_TOTAL);
  }

  public static Page<JobDto> getJobsDtoPage() {
    List<JobDto> jobs = List.of(getNewestJobDto(), getOldestJobDto());
    return new PageImpl<>(jobs, DEFAULT_PAGEABLE, DEFAULT_TOTAL);
  }

  public static List<Job> getTenJobsEntity() {
    return Stream.generate(TestDataGenerator::getNewestJobEntity).limit(10).toList();
  }

  public static List<JobDto> getTenJobsDto() {
    return Stream.generate(TestDataGenerator::getNewestJobDto).limit(10).toList();
  }

  public static Map<String, Long> getStatisticsMap() {
    return Map.of("NY", 5L, "IL", 5L, "CA", 3L, "TX", 1L);
  }
}
