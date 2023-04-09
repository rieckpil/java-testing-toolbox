package de.rieckpil.blog.selenide;

import com.codeborne.selenide.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.testcontainers.Testcontainers;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.utility.DockerImageName;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class TestcontainersDashboardControllerWT {

  private static final ChromeOptions CHROME_OPTIONS =
      new ChromeOptions()
          .addArguments("--headless")
          .addArguments("--no-sandbox")
          .addArguments("--disable-dev-shm-usage")
          .addArguments("--remote-allow-origins=*");

  static BrowserWebDriverContainer<?> webDriverContainer =
      new BrowserWebDriverContainer<>(
              System.getProperty("os.arch").equals("aarch64")
                  ? DockerImageName.parse("seleniarm/standalone-chromium:latest")
                      .asCompatibleSubstituteFor("selenium/standalone-chrome")
                  : DockerImageName.parse("selenium/standalone-chrome:latest"))
          .withCapabilities(CHROME_OPTIONS);

  @BeforeAll
  static void configure(@Autowired Environment environment) {
    Integer port = environment.getProperty("local.server.port", Integer.class);

    Testcontainers.exposeHostPorts(port);

    webDriverContainer.start();

    Configuration.timeout = 2000;
    Configuration.baseUrl = String.format("http://host.testcontainers.internal:%d", port);

    RemoteWebDriver remoteWebDriver =
        new RemoteWebDriver(webDriverContainer.getSeleniumAddress(), CHROME_OPTIONS, false);
    WebDriverRunner.setWebDriver(remoteWebDriver);
  }

  @Test
  void accessDashboardPage() {

    Selenide.open("/dashboard");

    Selenide.$(By.tagName("button")).click();

    Selenide.$(By.tagName("h1")).shouldHave(Condition.text("Welcome to the Dashboard!"));
    Selenide.$(By.tagName("h1")).shouldNotHave(Condition.cssClass("navy-blue"));

    Selenide.$(By.tagName("h1")).shouldBe(Condition.visible);
    Selenide.$(By.tagName("h1")).shouldNotBe(Condition.hidden);

    Selenide.$$(By.tagName("h1")).shouldHave(CollectionCondition.size(1));
  }

  @Test
  void accessDashboardPageAndLoadCustomers() {
    Selenide.open("/dashboard");

    // customer table should not be part of the DOM
    Selenide.$(By.id("all-customers")).shouldNot(Condition.exist);

    Selenide.screenshot("pre-customer-fetch");

    // let's fetch some customers
    Selenide.$(By.id("fetch-customers")).click();

    Selenide.screenshot("post-customer-fetch");

    // the customer table should not be part of the DOM
    Selenide.$(By.id("all-customers")).should(Condition.exist);

    Selenide.$$(By.className("customer-information")).shouldHave(CollectionCondition.size(3));
  }
}
