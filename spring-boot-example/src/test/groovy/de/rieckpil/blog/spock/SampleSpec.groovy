package de.rieckpil.blog.spock

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
}
