package de.rieckpil.blog.utility;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class TestExecutionTimeExtension implements BeforeAllCallback, AfterAllCallback {

  private long startTime;

  @Override
  public void beforeAll(ExtensionContext context) {
    startTime = System.currentTimeMillis();
    System.out.println("Starting tests for class: " + context.getTestClass().orElseThrow().getName());
  }

  @Override
  public void afterAll(ExtensionContext context) {
    long endTime = System.currentTimeMillis();
    long duration = endTime - startTime;
    System.out.println("Finished tests for class: " + context.getTestClass().orElseThrow().getName());
    System.out.println("Total execution time: " + duration + " ms");
  }
}
