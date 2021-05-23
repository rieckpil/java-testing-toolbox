package de.rieckpil.blog.thumbnail;

import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;

@Component
public class ImageUploadEventListener {

  @SqsListener("image-upload-events")
  public void processImageUploadEvent(ImageUploadEvent event) {

    System.out.println(event);

  }
}
