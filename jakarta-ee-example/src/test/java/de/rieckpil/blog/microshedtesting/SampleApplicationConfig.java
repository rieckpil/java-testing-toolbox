package de.rieckpil.blog.microshedtesting;

import de.rieckpil.blog.QuoteRestClient;
import org.microshed.testing.SharedContainerConfiguration;
import org.microshed.testing.testcontainers.ApplicationContainer;
import org.testcontainers.containers.MockServerContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

public class SampleApplicationConfig implements SharedContainerConfiguration {

  @Container
  public static PostgreSQLContainer<?> postgres =
    new PostgreSQLContainer<>(DockerImageName.parse("postgres:17"))
      .withNetworkAliases("postgres")
      .withUsername("duke")
      .withPassword("s3cr3t")
      .withDatabaseName("users");

  @Container
  public static MockServerContainer mockServer =
    new MockServerContainer(DockerImageName.parse("mockserver/mockserver:5.15.0"))
      .withNetworkAliases("mockserver");

  @Container
  public static ApplicationContainer app = new ApplicationContainer()
    .withEnv("POSTGRES_HOSTNAME", "postgres")
    .withEnv("POSTGRES_PORT", "5432")
    .withEnv("POSTGRES_USERNAME", "duke")
    .withEnv("POSTGRES_PASSWORD", "s3cr3t")
    .withEnv("POSTGRES_DATABASE", "users")
    .withEnv("message", "Hello World from MicroShed Testing")
    .withAppContextRoot("/")
    .withReadinessPath("/resources/sample/message")
    .withMpRestClient(QuoteRestClient.class, "http://mockserver:" + MockServerContainer.PORT);

}
