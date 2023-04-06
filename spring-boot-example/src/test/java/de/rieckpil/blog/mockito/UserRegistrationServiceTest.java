package de.rieckpil.blog.mockito;

import de.rieckpil.blog.registration.User;
import de.rieckpil.blog.registration.UserRegistrationService;
import de.rieckpil.blog.registration.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserRegistrationServiceTest {

  @Mock private UserRepository userRepository;

  @InjectMocks private UserRegistrationService cut;

  @Test
  void shouldNotReCreateExistingUserWrongStubbing() {
    Mockito.when(userRepository.findByUsername("mike")).thenReturn(new User());

    // Our mock will return null as the .findByUsername() stubbing setup doesn't match
    User result = this.cut.registerUser("duke");

    // As we don't provide a stubbing for .save() the repository returns null
    assertNull(result);
  }

  @Test
  void shouldNotReCreateExistingUser() {
    Mockito.when(userRepository.findByUsername("duke")).thenReturn(new User());

    User result = this.cut.registerUser("duke");
  }

  @Test
  void shouldNotReCreateExistingUserGeneric() {
    Mockito.when(userRepository.findByUsername(ArgumentMatchers.anyString()))
        .thenReturn(new User());

    User result = this.cut.registerUser("duke");
  }

  @Test
  void shouldFailDueToUnnecessaryStubbing() {
    Mockito.when(userRepository.findByEmail(ArgumentMatchers.anyString())).thenReturn(new User());

    // we won't use the .findByEmail() method internally
    User result = this.cut.registerUser("duke");
  }

  @Test
  void shouldPropagateException() {
    Mockito.when(userRepository.findByUsername("devil"))
        .thenThrow(new RuntimeException("DEVIL'S SQL EXCEPTION"));

    assertThrows(RuntimeException.class, () -> cut.registerUser("devil"));

    Mockito.verify(userRepository, never()).save(ArgumentMatchers.any(User.class));
    Mockito.verify(userRepository, times(1)).findByUsername("devil");
  }

  @Test
  void shouldCreateUnknownUser() {
    Mockito.when(userRepository.findByUsername("duke")).thenReturn(null);
    Mockito.when(userRepository.save(ArgumentMatchers.any(User.class)))
        .thenAnswer(
            context -> {
              User user = context.getArgument(0);
              user.setId(42L);
              return user;
            });

    User result = this.cut.registerUser("duke");

    assertEquals(42, result.getId());
  }
}
