package de.rieckpil.blog.selenide;

import com.codeborne.selenide.*;
import org.apache.commons.lang3.SystemUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@Testcontainers(disabledWithoutDocker = true)
@SpringBootTest(webEnvironment = RANDOM_PORT)
class TestcontainersDashboardControllerWT {

  @LocalServerPort private Integer port;

  private static final ChromeOptions CHROME_OPTIONS =
      new ChromeOptions()
          .addArguments("--headless")
          .addArguments("--no-sandbox")
          .addArguments("--disable-dev-shm-usage")
          .addArguments("--remote-allow-origins=*");

  @Container
  static BrowserWebDriverContainer<?> webDriverContainer =
      new BrowserWebDriverContainer<>().withCapabilities(CHROME_OPTIONS);

  @BeforeAll
  static void configure() {
    Configuration.timeout = 2000;

    RemoteWebDriver remoteWebDriver =
        new RemoteWebDriver(webDriverContainer.getSeleniumAddress(), CHROME_OPTIONS, false);
    WebDriverRunner.setWebDriver(remoteWebDriver);
  }

  @Test
  void accessDashboardPage() {

    Selenide.open("http://" + getHost() + ":" + port + "/dashboard");

    Selenide.$(By.tagName("button")).click();

    Selenide.$(By.tagName("h1")).shouldHave(Condition.text("Welcome to the Dashboard!"));
    Selenide.$(By.tagName("h1")).shouldNotHave(Condition.cssClass("navy-blue"));

    Selenide.$(By.tagName("h1")).shouldBe(Condition.visible);
    Selenide.$(By.tagName("h1")).shouldNotBe(Condition.hidden);

    Selenide.$$(By.tagName("h1")).shouldHave(CollectionCondition.size(1));
  }

  @Test
  void accessDashboardPageAndLoadCustomers() {
    Selenide.open("http://" + getHost() + ":" + port + "/dashboard");

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

  private String getHost() {
    return SystemUtils.IS_OS_LINUX ? "172.17.0.1" : "host.docker.internal";
  }
}
