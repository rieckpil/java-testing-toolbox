package de.rieckpil.blog.junit5;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.extension.ExtendWith;

import static de.rieckpil.blog.junit5.RandomUUIDParameterResolver.RandomUUID;

@ExtendWith(RandomUUIDParameterResolver.class)
class ExtensionExampleTest {

  @RepeatedTest(5)
  public void testUUIDInjection(@RandomUUID String uuid) {
    System.out.println("Injected random UUID: " + uuid);
  }
}
