package de.rieckpil.blog.testng;

import de.rieckpil.blog.registration.User;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class TestNGExampleTest {

  @BeforeClass
  public void setUp() {
    System.out.println("Invoked before this test is instantiated");
  }

  @BeforeTest
  public void beforeTest() {
    System.out.println("Invoked before each test");
  }

  @Test
  public void assertionExamples() {

    User userOne = new User();
    User userTwo = userOne;

    int[] openInvoiceIds = {42, 13, 7};

    Assert.assertEquals(42L, 40L + 2L, "Message on failure");
    Assert.assertNotEquals("duke", "gopher");

    Assert.assertTrue(4 % 2 == 0, "Java's math is broken");

    Assert.assertNotNull(new BigDecimal("42"));

    Assert.assertThrows(ArithmeticException.class, () -> {
      int result = 4 / 0;
    });

// checks for equal object references using ==
    Assert.assertSame(userOne, userTwo);

    Assert.assertEquals(new int[]{42, 13, 7}, openInvoiceIds);
    Assert.assertEqualsNoOrder(new Integer[]{42, 13, 7}, new Integer[]{13, 7, 42});

    Assert.assertEqualsDeep(
      Map.of("name", "duke", "age", 42),
      Map.of("age", 42, "name", "duke")
    );

  }

  @Test
  public void firstTest() {
    Assert.assertEquals("DUKE", "duke".toUpperCase());
  }

  @Test
  public void secondTest() {
    Assert.assertEquals("DUKE", "duke".toUpperCase());
  }

  @Test(dependsOnMethods = {"firstTest", "secondTest"})
  public void thirdMethod() {
    Assert.assertEquals("DUKE", "duke".toUpperCase());
  }

  @Test(timeOut = 1000, enabled = false, invocationCount = 5, threadPoolSize = 5, successPercentage = 60)
  public void parallelTest() {
    int randomNumber = ThreadLocalRandom.current().nextInt(300, 1500);
    System.out.println("Random number is: " + randomNumber);
    Assert.assertTrue(randomNumber < 200);
  }

  @Test(enabled = false, description = "∞", expectedExceptions = {ArithmeticException.class, IllegalArgumentException.class})
  public void temporaryDisabled() {
    Assert.assertEquals("INFINITY", 4 / 0);
  }
}
