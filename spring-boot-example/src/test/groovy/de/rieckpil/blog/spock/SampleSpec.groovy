package de.rieckpil.blog.spock

import spock.lang.Ignore
import spock.lang.Specification

class SampleSpec extends Specification {

  def "should uppercase a String"() {
    given:
    def string = "duke"

    when:
    def result = string.toUpperCase()

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

  def setupSpec() {
    print "Will run only once before all tests of this class"
  }

  def setup() {
    print "Will run before each test"
  }

  def cleanup() {
    print "Will run after each test"
  }

  def cleanupSpec() {
    print "Will run only once after all tests of this class"
  }
}
