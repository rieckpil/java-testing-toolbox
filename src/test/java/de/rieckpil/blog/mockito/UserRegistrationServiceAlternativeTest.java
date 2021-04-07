package de.rieckpil.blog.mockito;

import de.rieckpil.blog.registration.UserRegistrationService;
import de.rieckpil.blog.registration.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserRegistrationServiceAlternativeTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserRegistrationService cut;

  private AutoCloseable openMocks;

  @BeforeEach
  void setupMocks() {
    // .openMocks returns a AutoClosable which should be closed after the test
    this.openMocks = MockitoAnnotations.openMocks(this);
  }

  @AfterEach
  void tearDown() throws Exception {
    this.openMocks.close();
  }

  @Test
  void verifyInstantiation() {
    assertNotNull(userRepository);
    assertNotNull(cut);
  }

  @Test
  void manualSetup() {
    UserRepository userRepository = Mockito.mock(UserRepository.class);
    UserRegistrationService cut = new UserRegistrationService(userRepository);
  }
}
