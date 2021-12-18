package de.rieckpil.blog.spock

import de.rieckpil.blog.registration.User
import de.rieckpil.blog.registration.UserRegistrationService
import de.rieckpil.blog.registration.UserRepository
import spock.lang.Specification

import java.time.LocalDateTime

class UserRegistrationServiceSpec extends Specification {

  UserRepository userRepositoryStub = Stub()
  UserRepository userRepositoryMock = Mock()

  def "should return the user from the DB if the user already exists (Stub)"() {
    given:
    User savedUser = new User("Bob", LocalDateTime.now().minusDays(3))
    UserRegistrationService unit = new UserRegistrationService(userRepositoryStub)
    userRepositoryStub.findByUsername("Bob") >> savedUser

    when:
    User result = unit.registerUser("Bob")

    then:
    result == savedUser
  }

  def "should return the user from the DB if the user already exists (Mock)"() {
    given:
    User savedUser = new User("Bob", LocalDateTime.now().minusDays(3))
    UserRegistrationService unit = new UserRegistrationService(userRepositoryMock)
    userRepositoryMock.findByUsername("Bob") >> savedUser

    when:
    User result = unit.registerUser("Bob")

    then:
    result == savedUser
    0 * userRepositoryMock.save
  }
}
