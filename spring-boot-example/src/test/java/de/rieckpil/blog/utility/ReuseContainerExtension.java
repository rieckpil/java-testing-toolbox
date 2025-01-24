package de.rieckpil.blog.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Properties;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;

public class ReuseContainerExtension implements BeforeAllCallback, BeforeEachCallback {

  private static final String TESTCONTAINERS_PROPERTIES_FILE = System.getProperty("user.home") + "/.testcontainers.properties";
  private static final String REUSE_PROPERTY_KEY = "testcontainers.reuse.enable";

  @Override
  public void beforeAll(ExtensionContext context) throws Exception {
    if (isReuseEnabled()) {
      processContainers(context.getTestClass().orElseThrow());
    }
  }

  @Override
  public void beforeEach(ExtensionContext context) throws Exception {
    if (isReuseEnabled()) {
      processContainers(context.getTestClass().orElseThrow());
    }
  }

  private boolean isReuseEnabled() {
    File propertiesFile = new File(TESTCONTAINERS_PROPERTIES_FILE);
    if (!propertiesFile.exists()) {
      System.out.println("Testcontainers properties file not found: " + TESTCONTAINERS_PROPERTIES_FILE);
      return false;
    }

    try (FileInputStream fis = new FileInputStream(propertiesFile)) {
      Properties properties = new Properties();
      properties.load(fis);
      return "true".equalsIgnoreCase(properties.getProperty(REUSE_PROPERTY_KEY));
    } catch (IOException e) {
      System.err.println("Failed to read Testcontainers properties file: " + e.getMessage());
      return false;
    }
  }

  private void processContainers(Class<?> testClass) throws Exception {
    for (Field field : testClass.getDeclaredFields()) {
      if (field.isAnnotationPresent(Container.class) && GenericContainer.class.isAssignableFrom(field.getType())) {
        field.setAccessible(true);
        GenericContainer<?> container = (GenericContainer<?>) field.get(null);
        if (container != null) {
          container.withReuse(true);
          System.out.println("Reuse enabled for container: " + container.getDockerImageName());
        }
      }
    }
  }
}
