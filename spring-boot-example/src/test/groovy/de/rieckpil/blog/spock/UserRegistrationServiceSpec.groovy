package de.rieckpil.blog.spock

import de.rieckpil.blog.registration.User
import de.rieckpil.blog.registration.UserRegistrationService
import de.rieckpil.blog.registration.UserRepository
import spock.lang.Specification

class UserRegistrationServiceSpec extends Specification {

  def "should return the existing user from the database"() {
    given:
    def savedUser = new User("Bob")
    def userRepositoryStub = Stub(UserRepository)
    def cut = new UserRegistrationService(userRepositoryStub)
    userRepositoryStub.findByUsername("Bob") >> savedUser

    when:
    def result = cut.registerUser("Bob")

    then:
    result == savedUser
  }

  def "should save new user to the database"() {
    given:
    def userRepositoryMock = Mock(UserRepository)
    def cut = new UserRegistrationService(userRepositoryMock)

    when:
    def result = cut.registerUser("Bob")

    then:
    1 * userRepositoryMock.save({it -> it.username == "Bob"})
  }
}
