package de.rieckpil.blog.selenide;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@Disabled("Showcase only")
@SpringBootTest(webEnvironment = RANDOM_PORT)
class FirefoxDashboardControllerWT {

  @LocalServerPort
  private Integer port;

  @BeforeAll
  static void configure() {
    Configuration.browser = "firefox";
    Configuration.browserSize = "1337x1337";
    Configuration.timeout = 2000;
  }

  @Test
  void accessDashboardPage() {
    Selenide.open("http://localhost:" + port + "/dashboard");

    Selenide.$(By.tagName("button")).click();

    Selenide.$(By.tagName("h1")).shouldHave(Condition.text("Welcome to the Dashboard!"));
    Selenide.$(By.tagName("h1")).shouldNotHave(Condition.cssClass("navy-blue"));

    Selenide.$(By.tagName("h1")).shouldBe(Condition.visible);
    Selenide.$(By.tagName("h1")).shouldNotBe(Condition.hidden);

    Selenide.$$(By.tagName("h1")).shouldHave(CollectionCondition.size(1));
  }

  @Test
  void accessDashboardPageAndLoadCustomers() {
    Selenide.open("http://localhost:" + port + "/dashboard");

    // customer table should not be part of the DOM
    Selenide.$(By.id("all-customers")).shouldNot(Condition.exist);

    Selenide.screenshot("pre-customer-fetch");

    // let's fetch some customers
    Selenide.$(By.id("fetch-customers")).click();

    Selenide.screenshot("post-customer-fetch");

    // the customer table should now be part of the DOM
    Selenide.$(By.id("all-customers")).should(Condition.exist);

    Selenide.$$(By.className("customer-information")).shouldHave(CollectionCondition.size(3));
  }
}
