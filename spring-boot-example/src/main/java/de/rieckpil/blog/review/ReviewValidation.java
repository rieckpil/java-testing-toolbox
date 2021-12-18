package de.rieckpil.blog.review;

public class ReviewValidation {

  public boolean titleMeetsQualityStandards(String reviewTitle) {

    if (reviewTitle.length() < 10) {
      return false;
    }

    return !reviewTitle.toLowerCase().contains("lorem ipsum");
  }
}
