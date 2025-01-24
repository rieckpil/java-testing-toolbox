package de.rieckpil.blog.microshedtesting;

import de.rieckpil.blog.Person;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.microshed.testing.SharedContainerConfig;
import org.microshed.testing.jupiter.MicroShedTest;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@MicroShedTest
@Disabled("Rest Assured is not yet compatible with Jakarta EE 10")
@SharedContainerConfig(SampleApplicationConfig.class)
class PersonResourceIT {

  @Test
  void shouldCreatePerson() {

    Headers responseHeaders = given()
      .contentType(ContentType.JSON)
      .when()
      .body("{\"firstName\": \"duke\", \"lastName\":\"jakarta\"}")
      .post("/resources/persons")
      .then()
      .statusCode(201)
      .extract()
      .headers();

    String locationHeader = responseHeaders.getValue("Location");
    assertNotNull(locationHeader);

    long personId = Long.parseLong(locationHeader.substring(locationHeader.lastIndexOf('/') + 1));
    assertTrue(personId > 0, "Generated ID should be greater than 0 but was: " + personId);

    System.out.println(locationHeader);

    Person createdPerson = given()
      .accept(ContentType.JSON)
      .when()
      .get("/resources/persons/" + personId)
      .then()
      .statusCode(200)
      .extract()
      .as(Person.class);

    assertEquals("duke", createdPerson.getFirstName());
    assertEquals("jakarta", createdPerson.getLastName());
  }
}
