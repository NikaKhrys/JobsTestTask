package com.arbeitnow.testtask.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import java.math.BigInteger;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for transferring Job data
 *
 * @author NikaKhrys
 */
@Data
@NoArgsConstructor
@JsonRootName("job")
@Builder
@AllArgsConstructor
public class JobDto {
  @JsonIgnore private UUID id;
  private String slug;

  @JsonProperty("company_name")
  private String companyName;

  private String title;
  private String description;
  private boolean remote;
  private String url;
  private String location;

  @JsonProperty("created_at")
  private BigInteger createdAt;

  @JsonProperty("job_types")
  private Set<String> jobTypes;

  private Set<String> tags;
}
