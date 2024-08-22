package com.arbeitnow.testtask.service.exceptions;

/**
 * RuntimeException that occurs when parsing jobs data from external api wasn't successful
 *
 * @author NikaKhrys
 */
public class JobsParsingFailedException extends RuntimeException {

  private static final String MESSAGE = "Failed to parse jobs information from api";

  public JobsParsingFailedException(Exception e) {
    super(MESSAGE, e);
  }
}
