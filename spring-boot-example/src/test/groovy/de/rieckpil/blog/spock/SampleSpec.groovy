package de.rieckpil.blog.spock

import spock.lang.Ignore
import spock.lang.Specification

class SampleSpec extends Specification {

  def "should uppercase String"() {
    given:
    String s = new String("duke")

    when:
    def result = s.toUpperCase()

    then:
    result == "DUKE"
  }

  def "should fail with NPE when checking length of null String"() {
    given:
    String username = null

    when:
    def result = username.length()

    then:
    thrown(NullPointerException)
  }

  @Ignore("Showcase only")
  def "should fail with readable assertion error in the log"() {
    given:
    int firstValue = 1
    int secondValue = 42

    when:
    int expectedResult = 100

    then:
    Math.max(firstValue, secondValue) == expectedResult
  }
}
