package de.rieckpil.blog.localstack;

import java.io.IOException;
import java.time.Duration;

import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import static org.awaitility.Awaitility.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.testcontainers.containers.BindMode.READ_ONLY;
import static org.testcontainers.containers.localstack.LocalStackContainer.Service.S3;
import static org.testcontainers.containers.localstack.LocalStackContainer.Service.SQS;

@Testcontainers
@SpringBootTest
class ManualBeanOverrideIT {

  @Container
  static LocalStackContainer localStack =
      new LocalStackContainer(DockerImageName.parse("localstack/localstack:2.0.0"))
          .withServices(LocalStackContainer.Service.S3, LocalStackContainer.Service.SQS)
          .withClasspathResourceMapping("/localstack", "/etc/localstack/init/ready.d", READ_ONLY);

  @Autowired private S3Client s3Client;

  @Autowired private SqsTemplate sqsTemplate;

  @DynamicPropertySource
  static void configureLocalStackAccess(DynamicPropertyRegistry registry) {
    registry.add("spring.cloud.aws.s3.endpoint", () -> localStack.getEndpointOverride(S3));
    registry.add("spring.cloud.aws.sqs.endpoint", () -> localStack.getEndpointOverride(SQS));
  }

  @Test
  void shouldProcessIncomingUploadEventAndUploadThumbnailImage() throws IOException {

    s3Client.putObject(
        PutObjectRequest.builder().bucket("raw-images").key("duke-mascot.png").build(),
        RequestBody.fromFile(new ClassPathResource("images/duke-mascot.png").getFile()));

    sqsTemplate.send(
        "/image-upload-events", "{\"s3Bucket\": \"raw-images\", \"s3Key\": \"duke-mascot.png\"}");

    given()
        .atMost(Duration.ofSeconds(5))
        .await()
        .untilAsserted(
            () ->
                assertNotNull(
                    s3Client.getObject(
                        GetObjectRequest.builder()
                            .bucket("processed-images")
                            .key("thumbnail-duke-mascot.png")
                            .build())));
  }
}
