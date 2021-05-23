package de.rieckpil.blog.thumbnail;

public class ImageUploadEvent {

  private String s3Bucket;
  private String s3Key;

  public ImageUploadEvent() {
  }

  public ImageUploadEvent(String s3Bucket, String s3Key) {
    this.s3Bucket = s3Bucket;
    this.s3Key = s3Key;
  }

  public String getS3Bucket() {
    return s3Bucket;
  }

  public void setS3Bucket(String s3Bucket) {
    this.s3Bucket = s3Bucket;
  }

  public String getS3Key() {
    return s3Key;
  }

  public void setS3Key(String s3Key) {
    this.s3Key = s3Key;
  }

  @Override
  public String toString() {
    return "ImageUploadEvent{" +
      "s3Bucket='" + s3Bucket + '\'' +
      ", s3Key='" + s3Key + '\'' +
      '}';
  }
}
