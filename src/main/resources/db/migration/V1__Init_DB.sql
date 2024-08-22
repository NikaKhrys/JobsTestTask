CREATE TABLE jobs(
id VARCHAR (36) NOT NULL,
slug VARCHAR (256) NOT NULL,
company_name VARCHAR (256) NOT NULL,
title VARCHAR (256) NOT NULL,
description VARCHAR (MAX) NOT NULL,
remote BOOLEAN NOT NULL,
url VARCHAR (256) NOT NULL,
location VARCHAR (256) NOT NULL,
created_at BIGINT NOT NULL,
PRIMARY KEY (id)
);

CREATE TABLE jobs_types(
job_id VARCHAR (36) NOT NULL,
job_type VARCHAR (36) NOT NULL,
CONSTRAINT `fk_jobs_types_jobs`
  FOREIGN KEY (job_id) REFERENCES jobs (id)
  ON DELETE CASCADE,
PRIMARY KEY (job_id, job_type)
);

CREATE TABLE jobs_tags(
job_id VARCHAR (36) NOT NULL,
tag VARCHAR (256) NOT NULL,
CONSTRAINT `fk_jobs_tags_jobs`
  FOREIGN KEY (job_id) REFERENCES jobs (id)
  ON DELETE CASCADE,
PRIMARY KEY (job_id, tag)
);