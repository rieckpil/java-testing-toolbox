package de.rieckpil.blog.thumbnail;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;

@Service
public class ImageResizer {

  private static final Integer THUMBNAIL_SIZE = 300;

  public File createThumbnail(File file) throws IOException {

    BufferedImage img = new BufferedImage(THUMBNAIL_SIZE, THUMBNAIL_SIZE, BufferedImage.TYPE_INT_RGB);

    img
      .createGraphics()
      .drawImage(ImageIO.read(file).getScaledInstance(THUMBNAIL_SIZE, THUMBNAIL_SIZE, Image.SCALE_SMOOTH), 0, 0, null);

    File resizedTempFile = File.createTempFile(UUID.randomUUID().toString(), ".resized.tmp");
    ImageIO.write(img, "png", resizedTempFile);

    return resizedTempFile;
  }
}
