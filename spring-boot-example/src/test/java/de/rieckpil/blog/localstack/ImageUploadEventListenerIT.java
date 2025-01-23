package de.rieckpil.blog.localstack;

import java.io.IOException;
import java.time.Duration;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.awaitility.Awaitility.given;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testcontainers.containers.BindMode.READ_ONLY;
import static org.testcontainers.containers.localstack.LocalStackContainer.Service;
import static org.testcontainers.containers.localstack.LocalStackContainer.Service.S3;
import static org.testcontainers.containers.localstack.LocalStackContainer.Service.SQS;

@Testcontainers
@SpringBootTest
public class ImageUploadEventListenerIT {

  @Container
  static LocalStackContainer localStack =
    new LocalStackContainer(DockerImageName.parse("localstack/localstack:4.0.1"))
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

  @Autowired
  private AmazonS3 s3Client;

  @Autowired
  private AmazonSQS sqsClient;

  @Test
  void shouldProcessIncomingUploadEventAndUploadThumbnailImage() throws IOException {

    s3Client
      .putObject("raw-images", "duke-mascot.png", new ClassPathResource("images/duke-mascot.png")
        .getFile());

    sqsClient
      .sendMessage(new SendMessageRequest()
        .withQueueUrl("http://localhost:" + localStack.getMappedPort(4566) + "/000000000000/image-upload-events")
        .withMessageBody("{\"s3Bucket\": \"raw-images\", \"s3Key\": \"duke-mascot.png\"}"));

    given()
      .atMost(Duration.ofSeconds(5))
      .await()
      .untilAsserted(() -> assertTrue(s3Client.doesObjectExist("processed-images", "thumbnail-duke-mascot.png")));
  }
}
