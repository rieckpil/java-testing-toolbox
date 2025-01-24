package de.rieckpil.blog.localstack;

import java.io.IOException;
import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import static org.awaitility.Awaitility.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.testcontainers.containers.BindMode.READ_ONLY;

@Testcontainers
@SpringBootTest(properties = "cloud.aws.sqs.enabled=true")
class ManualBeanOverrideIT {

  @Container
  static LocalStackContainer localStack =
    new LocalStackContainer(DockerImageName.parse("localstack/localstack:4.0.1"))
      .withServices(LocalStackContainer.Service.S3, LocalStackContainer.Service.SQS)
      .withClasspathResourceMapping("/localstack", "/docker-entrypoint-initaws.d", READ_ONLY)
      .waitingFor(Wait.forLogMessage(".*Initialized\\.\n", 1));

  @Autowired
  private S3Client s3Client;

  @Autowired
  private SqsClient sqsClient;

  @Test
  void shouldProcessIncomingUploadEventAndUploadThumbnailImage() throws IOException {

    s3Client
      .putObject(PutObjectRequest.builder()
          .bucket("raw-images")
          .key("duke-mascot.png")
          .build(),
        RequestBody.fromFile(new ClassPathResource("images/duke-mascot.png")
          .getFile()));

    sqsClient
      .sendMessage(SendMessageRequest.builder()
        .queueUrl("http://localhost:" + localStack.getMappedPort(4566) + "/000000000000/image-upload-events")
        .messageBody("{\"s3Bucket\": \"raw-images\", \"s3Key\": \"duke-mascot.png\"}")
        .build());

    given()
      .atMost(Duration.ofSeconds(5))
      .await()
      .untilAsserted(() -> assertNotNull(s3Client.getObject(GetObjectRequest.builder().bucket("processed-images").key("thumbnail-duke-mascot.png").build())));
  }
}
