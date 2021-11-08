package de.rieckpil.blog.thumbnail;

import java.io.File;
import java.io.IOException;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ImageUploadEventListener {

  private static final Logger LOG = LoggerFactory.getLogger(ImageUploadEventListener.class);

  private final AmazonS3 s3Client;
  private final ImageResizer imageResizer;

  public ImageUploadEventListener(
    AmazonS3 s3Client,
    ImageResizer imageResizer) {
    this.s3Client = s3Client;
    this.imageResizer = imageResizer;
  }

  @SqsListener("image-upload-events")
  public void processImageUploadEvent(ImageUploadEvent event) throws IOException {

    File fileToBeResized = File.createTempFile(event.getS3Key(), ".tmp");
    s3Client.getObject(new GetObjectRequest(event.getS3Bucket(), event.getS3Key()), fileToBeResized);

    File resizedFile = imageResizer.createThumbnail(fileToBeResized);
    s3Client.putObject("processed-images", "thumbnail-" + event.getS3Key(), resizedFile);

    LOG.info("Successfully uploaded thumbnail to S3 for file: '{}'", event.getS3Bucket() + "/" + event.getS3Key());
  }
}
