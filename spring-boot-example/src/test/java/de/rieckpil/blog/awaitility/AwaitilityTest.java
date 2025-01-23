package de.rieckpil.blog.awaitility;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.org.awaitility.Awaitility;

import static org.assertj.core.api.Assertions.assertThat;

class AwaitilityTest {

  @Test
  void shouldUpdateValueAsynchronously() {
    AtomicBoolean flag = new AtomicBoolean(false);

    // Simulate an asynchronous task
    new Thread(() -> {
      try {
        Thread.sleep(500);
        flag.set(true);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }).start();

    // Awaitility to the rescue
    Awaitility.await()
      .atMost(1, TimeUnit.SECONDS)
      .until(flag::get);
  }

  @Test
  void showcaseOtherUsages() {

    AtomicBoolean flag = new AtomicBoolean(false);

    // Simulate an asynchronous task
    new Thread(() -> {
      try {
        Thread.sleep(500);
        flag.set(true);
      }
      catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }).start();

    // Awaitility to the rescue
    Awaitility.await()
      .alias("Test")
      .with()
      .conditionEvaluationListener(condition ->
        System.out.printf(
          "Checking condition '%s' (elapsed: %dms, remaining: %dms)\n",
          condition.getAlias(),
          condition.getElapsedTimeInMS(),
          condition.getRemainingTimeInMS()
        ))
      .ignoreExceptions()
      .pollInterval(100, TimeUnit.MILLISECONDS)
      .atMost(2, TimeUnit.SECONDS)
      .until(flag::get);

  }
}
