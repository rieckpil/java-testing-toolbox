package de.rieckpil.blog.spock

import de.rieckpil.blog.registration.User
import de.rieckpil.blog.registration.UserRegistrationService
import de.rieckpil.blog.registration.UserRepository
import spock.lang.Specification

class UserRegistrationServiceSpec extends Specification {

  def userRepositoryMock = Mock(UserRepository)

  def "should return the user from the DB if the user already exists (Mock)"() {
    given:
    def savedUser = new User("Bob")
    def cut = new UserRegistrationService(userRepositoryMock)
    userRepositoryMock.findByUsername("Bob") >> savedUser

    when:
    def result = cut.registerUser("Bob")

    then:
    result == savedUser
    0 * userRepositoryMock.save
  }
}
