package de.rieckpil.blog.spock

import de.rieckpil.blog.review.ReviewValidation
import spock.lang.Specification
import spock.lang.Unroll

class ReviewValidationSpec extends Specification {
  private ReviewValidation cut = new ReviewValidation()

  @Unroll("the title #title should meet the quality standards: #expected")
  def "should identify the quality of review titles"(String title, boolean expected) {
    expect:
    cut.titleMeetsQualityStandards(title) == expected

    where:
    title                    | expected
    "ABCD"                   | false
    "Bad book"               | false
    "lorem ipsum"            | false
    "Java Testing Ecosystem" | true
  }
}
