package de.rieckpil.blog.localstack;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.testcontainers.containers.BindMode.READ_ONLY;
import static org.testcontainers.containers.localstack.LocalStackContainer.Service;
import static org.testcontainers.containers.localstack.LocalStackContainer.Service.S3;
import static org.testcontainers.containers.localstack.LocalStackContainer.Service.SQS;

@Testcontainers
@SpringBootTest
public class ImageUploadEventListenerIT {

  @Container
  static LocalStackContainer localStack =
    new LocalStackContainer(DockerImageName.parse("localstack/localstack:0.12.19"))
      .withServices(Service.S3, Service.SQS)
      .withClasspathResourceMapping("/localstack", "/docker-entrypoint-initaws.d", READ_ONLY)
      .waitingFor(Wait.forLogMessage(".*Initialized\\.\n", 1));

  @DynamicPropertySource
  static void configureLocalStackAccess(DynamicPropertyRegistry registry) {
    registry.add("cloud.aws.sqs.enabled", () -> true);
    registry.add("cloud.aws.s3.endpoint", () -> localStack.getEndpointOverride(S3));
    registry.add("cloud.aws.sqs.endpoint", () -> localStack.getEndpointOverride(SQS));
    registry.add("cloud.aws.credentials.access-key", () -> localStack.getAccessKey());
    registry.add("cloud.aws.credentials.secret-key", () -> localStack.getSecretKey());
  }

  @Test
  void shouldProcessIncomingUploadEventAndUploadThumbnailImage() throws IOException, InterruptedException {

    localStack.execInContainer("awslocal", "sqs", "send-message",
      "--queue-url", "http://localhost:4566/123456789012/image-upload-events",
      "--message-body", "{\"s3Bucket\": \"raw-images\", \"s3Key\": \"duke-with-cats.png\"}");

  }
}
