package de.rieckpil.blog.microshedtesting;

import de.rieckpil.blog.QuoteRestClient;
import org.microshed.testing.SharedContainerConfiguration;
import org.microshed.testing.testcontainers.ApplicationContainer;
import org.testcontainers.containers.MockServerContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

class SampleApplicationConfig implements SharedContainerConfiguration {

  @Container
  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>()
    .withNetworkAliases("mypostgres")
    .withExposedPorts(5432)
    .withUsername("duke")
    .withPassword("duke42")
    .withDatabaseName("users");

  @Container
  static MockServerContainer mockServer = new MockServerContainer()
    .withNetworkAliases("mockserver");

  @Container
  static ApplicationContainer app = new ApplicationContainer()
    .withEnv("POSTGRES_HOSTNAME", "mypostgres")
    .withEnv("POSTGRES_PORT", "5432")
    .withEnv("POSTGRES_USERNAME", "duke")
    .withEnv("POSTGRES_PASSWORD", "duke42")
    .withEnv("message", "Hello World from MicroShed Testing")
    .withAppContextRoot("/")
    .withReadinessPath("/resources/sample/message")
    .withMpRestClient(QuoteRestClient.class, "http://mockserver:" + MockServerContainer.PORT);

}
