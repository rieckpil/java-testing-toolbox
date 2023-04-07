package de.rieckpil.blog.thumbnail;

import java.io.IOException;

import io.awspring.cloud.sqs.annotation.SqsListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

// @Component
public class ImageUploadEventListener {

  private static final Logger LOG = LoggerFactory.getLogger(ImageUploadEventListener.class);

  private final S3Client s3Client;
  private final ImageResizer imageResizer;

  public ImageUploadEventListener(S3Client s3Client, ImageResizer imageResizer) {
    this.s3Client = s3Client;
    this.imageResizer = imageResizer;
  }

  @SqsListener("image-upload-events")
  public void processImageUploadEvent(ImageUploadEvent event) throws IOException {

    LOG.info("Consumed event: {}", event);

    ResponseBytes<GetObjectResponse> objectAsBytes =
        s3Client.getObjectAsBytes(
            GetObjectRequest.builder().bucket(event.getS3Bucket()).key(event.getS3Key()).build());

    byte[] resizedFile = imageResizer.createThumbnail(objectAsBytes.asInputStream());

    s3Client.putObject(
        PutObjectRequest.builder()
            .bucket("processed-images")
            .key("thumbnail-" + event.getS3Key())
            .build(),
        RequestBody.fromBytes(resizedFile));

    LOG.info(
        "Successfully uploaded thumbnail to S3 for file: '{}'",
        event.getS3Bucket() + "/" + event.getS3Key());
  }
}
