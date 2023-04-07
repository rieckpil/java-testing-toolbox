package de.rieckpil.blog.thumbnail;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

@Service
public class ImageResizer {

  private static final Integer THUMBNAIL_SIZE = 300;

  public byte[] createThumbnail(InputStream inputStream) throws IOException {

    BufferedImage img =
        new BufferedImage(THUMBNAIL_SIZE, THUMBNAIL_SIZE, BufferedImage.TYPE_INT_RGB);

    img.createGraphics()
        .drawImage(
            ImageIO.read(inputStream)
                .getScaledInstance(THUMBNAIL_SIZE, THUMBNAIL_SIZE, Image.SCALE_SMOOTH),
            0,
            0,
            null);

    File resizedTempFile = File.createTempFile(UUID.randomUUID().toString(), ".resized.tmp");

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    ImageIO.write(img, "png", outputStream);

    return outputStream.toByteArray();
  }
}
