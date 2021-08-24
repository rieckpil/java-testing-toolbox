package de.rieckpil.blog.microshedtesting;

import de.rieckpil.blog.SampleResource;
import org.junit.jupiter.api.Test;
import org.microshed.testing.jaxrs.RESTClient;
import org.microshed.testing.jupiter.MicroShedTest;
import org.microshed.testing.testcontainers.ApplicationContainer;
import org.testcontainers.junit.jupiter.Container;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicroShedTest
public class BasicIT {

  @RESTClient
  public static SampleResource sampleResource;

  @Container
  public static ApplicationContainer app = new ApplicationContainer()
    .withAppContextRoot("/");

  @Test
  public void helloWorld() {
    assertEquals("Hello World!", sampleResource.message().readEntity(String.class));
  }

}
