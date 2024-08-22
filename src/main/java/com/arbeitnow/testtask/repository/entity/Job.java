package com.arbeitnow.testtask.repository.entity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import java.math.BigInteger;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity for retrieving Job data
 *
 * @author NikaKhrys
 */
@Entity
@Table(name = "jobs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Job {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String slug;

  @Column(name = "company_name")
  private String companyName;

  private String title;
  private String description;
  private boolean remote;
  private String url;
  private String location;

  @Column(name = "created_at")
  private BigInteger createdAt;

  @ElementCollection
  @CollectionTable(name = "jobs_types", joinColumns = @JoinColumn(name = "job_id"))
  @Column(name = "job_type")
  private Set<String> jobTypes;

  @ElementCollection
  @CollectionTable(name = "jobs_tags", joinColumns = @JoinColumn(name = "job_id"))
  @Column(name = "tag")
  private Set<String> tags;
}
