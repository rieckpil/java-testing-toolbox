package de.rieckpil.blog.microshedtesting;

import javax.ws.rs.core.Response;

import de.rieckpil.blog.Person;
import de.rieckpil.blog.PersonResource;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.microshed.testing.SharedContainerConfig;
import org.microshed.testing.jaxrs.RESTClient;
import org.microshed.testing.jupiter.MicroShedTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Disabled
@MicroShedTest
@SharedContainerConfig(SampleApplicationConfig.class)
class PersonResourceIT {

  @RESTClient
  public static PersonResource personsEndpoint;

  @Test
  void shouldCreatePerson() {
    Person duke = new Person();
    duke.setFirstName("duke");
    duke.setLastName("jakarta");

    Response result = personsEndpoint.createNewPerson(null, duke);

    assertEquals(Response.Status.CREATED.getStatusCode(), result.getStatus());
    var createdUrl = result.getHeaderString("Location");
    assertNotNull(createdUrl);

    var id = Long.valueOf(createdUrl.substring(createdUrl.lastIndexOf('/') + 1));
    assertTrue(id > 0, "Generated ID should be greater than 0 but was: " + id);

    var newPerson = personsEndpoint.getPersonById(id);
    assertNotNull(newPerson);
    assertEquals("duke", newPerson.getFirstName());
    assertEquals("jakarta", newPerson.getLastName());
  }
}
