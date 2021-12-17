package de.rieckpil.blog.spock

import de.rieckpil.blog.review.ReviewValidation
import spock.lang.Specification
import spock.lang.Unroll

class ReviewValidationSpec extends Specification {
  private ReviewValidation cut = new ReviewValidation()

  @Unroll
  def "should return #expected when the title is #title"(String title, boolean expected) {
    expect:
    cut.titleMeetsQualityStandards(title) == expected

    where:
    title                    | expected
    "ABCD"                   | false
    "Bad book"               | false
    ":("                     | false
    "Java Testing Ecosystem" | true
  }
}
