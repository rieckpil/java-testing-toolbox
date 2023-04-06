package de.rieckpil.blog;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

@Path("/persons")
@ApplicationScoped
@Transactional(TxType.REQUIRED)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PersonResource {

  @PersistenceContext private EntityManager entityManager;

  @GET
  public List<Person> getAllPersons() {
    return entityManager.createQuery("SELECT p FROM Person p", Person.class).getResultList();
  }

  @GET
  @Path("/{id}")
  public Person getPersonById(@PathParam("id") Long id) {
    var personById = entityManager.find(Person.class, id);

    if (personById == null) {
      throw new NotFoundException();
    }

    return personById;
  }

  @POST
  public Response createNewPerson(@Context UriInfo uriInfo, Person personToStore) {
    entityManager.persist(personToStore);

    var headerLocation =
        uriInfo.getAbsolutePathBuilder().path(personToStore.getId().toString()).build();

    return Response.created(headerLocation).build();
  }
}
