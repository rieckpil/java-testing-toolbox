package de.rieckpil.blog.testcontainers;

import java.io.IOException;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.Container.ExecResult;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
class BasicContainerIT {

  private static final Logger LOG = LoggerFactory.getLogger(BasicContainerIT.class);

  @Container
  static GenericContainer<?> keycloak =
      new GenericContainer<>(DockerImageName.parse("quay.io/keycloak/keycloak:17.0.0-legacy"))
          .waitingFor(Wait.forHttp("/auth").forStatusCode(200))
          .withExposedPorts(8080)
          .withClasspathResourceMapping("/config/test.txt", "/tmp/test.txt", BindMode.READ_WRITE)
          .withLogConsumer(new Slf4jLogConsumer(LOG))
          .withEnv(
              Map.of(
                  "KEYCLOAK_USER", "testcontainers",
                  "KEYCLOAK_PASSWORD", "testcontainers",
                  "DB_VENDOR", "h2"));

  @Test
  void testWithKeycloak() throws IOException, InterruptedException {

    ExecResult execResult =
        keycloak.execInContainer("/bin/sh", "-c", "echo \"Admin user is $KEYCLOAK_USER\"");

    System.out.println("Result: " + execResult.getStdout());
    System.out.println("Keycloak is running on port: " + keycloak.getMappedPort(8080));
  }
}
