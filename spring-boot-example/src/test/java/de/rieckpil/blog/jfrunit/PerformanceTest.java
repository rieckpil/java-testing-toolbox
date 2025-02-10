package de.rieckpil.blog.jfrunit;

import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.moditect.jfrunit.EnableEvent;
import org.moditect.jfrunit.JfrEventTest;
import org.moditect.jfrunit.JfrEvents;
import org.moditect.jfrunit.events.GarbageCollection;
import org.moditect.jfrunit.events.JfrEventTypes;
import org.moditect.jfrunit.events.ThreadSleep;

import static org.moditect.jfrunit.JfrEventsAssert.assertThat;

@JfrEventTest
public class PerformanceTest {

  public JfrEvents jfrEvents = new JfrEvents();

  @Test
  @EnableEvent(GarbageCollection.EVENT_NAME)
  @EnableEvent(ThreadSleep.EVENT_NAME)
  void shouldExecuteWithinTimeLimit() {

    new BusinessService().performTask();

    assertThat(jfrEvents)
      .contains(JfrEventTypes.GARBAGE_COLLECTION);

    assertThat(jfrEvents).contains(
      JfrEventTypes.THREAD_SLEEP.withTime(Duration.ofMillis(1000)));
  }
}
