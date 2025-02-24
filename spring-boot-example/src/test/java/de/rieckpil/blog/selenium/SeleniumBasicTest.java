package de.rieckpil.blog.selenium;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Disabled
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SeleniumBasicTest {

  @LocalServerPort
  private Integer port;

  private WebDriver driver;

  @BeforeEach
  void setUp() {
    driver = new ChromeDriver();
  }

  @Test
  void accessDashboardPage() {
    driver.get("http://localhost:" + port + "/dashboard");
  }

  @AfterEach
  void tearDown() {
    if (driver != null) {
      driver.quit();
    }
  }

  @Test
  void verifyPageHeading() {
    driver.get("http://localhost:" + port + "/dashboard");
    WebElement heading = driver.findElement(By.tagName("h1"));
    assertEquals("Welcome to the Dashboard!", heading.getText());
  }

  @Test
  void waitForElementToAppear() {
    driver.get("http://localhost:" + port + "/dashboard");
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("fetch-customers")));
    element.click();
  }

  @Test
  void executeJavaScript() {
    driver.get("http://localhost:" + port + "/dashboard");

    // Execute JavaScript and get return value
    JavascriptExecutor js = (JavascriptExecutor) driver;
    String pageTitle = (String) js.executeScript("return document.title");

    // Scroll to element
    WebElement element = driver.findElement(By.id("bottom-content"));
    js.executeScript("arguments[0].scrollIntoView(true);", element);

    // Modify page content
    js.executeScript("document.body.style.backgroundColor = 'red'");

    // Check if element is in viewport
    Boolean isInViewport = (Boolean) js.executeScript(
      "var rect = arguments[0].getBoundingClientRect(); " +
        "return (rect.top >= 0 && rect.left >= 0 && " +
        "rect.bottom <= window.innerHeight && rect.right <= window.innerWidth);",
      element
    );
  }

  @Test
  void windowManagement() {
    driver.get("http://localhost:" + port + "/dashboard");

    // Get window handle
    String mainWindow = driver.getWindowHandle();

    // Maximize window
    driver.manage().window().maximize();

    // Set specific size
    driver.manage().window().setSize(new Dimension(1024, 768));

    driver.manage().window().setPosition(new Point(100, 100));
  }

  @Test
  @Disabled
  void handleFrames() {
    driver.get("http://localhost:" + port + "/dashboard");

    // Switch to frame by index
    driver.switchTo().frame(0);

    // Switch to frame by name or ID
    driver.switchTo().frame("frame-name");

    // Switch to frame using WebElement
    WebElement frameElement = driver.findElement(By.cssSelector("#my-frame"));
    driver.switchTo().frame(frameElement);

    // Interact with elements inside frame
    WebElement elementInFrame = driver.findElement(By.id("frame-content"));
    elementInFrame.click();

    // Switch back to default content
    driver.switchTo().defaultContent();
  }

  @Test
  void manageBrowserStorage() {
    driver.get("http://localhost:" + port + "/dashboard");

    // Add cookie
    Cookie cookie = new Cookie.Builder("session-id", "abc123")
      .domain("localhost")
      .path("/")
      .expiresOn(Date.from(Instant.now().plus(Duration.ofDays(1))))
      .isSecure(true)
      .isHttpOnly(true)
      .build();
    driver.manage().addCookie(cookie);

    // Get all cookies
    Set<Cookie> cookies = driver.manage().getCookies();

    // Delete specific cookie
    driver.manage().deleteCookie(cookie);

    // Clear all cookies
    driver.manage().deleteAllCookies();

    // Local Storage operations using JavaScript
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("localStorage.setItem('key', 'value');");
    String value = (String) js.executeScript("return localStorage.getItem('key');");
    js.executeScript("localStorage.clear();");

    // Session Storage
    js.executeScript("sessionStorage.setItem('key', 'value');");
    js.executeScript("sessionStorage.clear();");
  }
}
