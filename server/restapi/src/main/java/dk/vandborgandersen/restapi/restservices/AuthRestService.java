package dk.vandborgandersen.restapi.restservices;

import dk.vandborgandersen.restapi.backendservices.AuthService;
import dk.vandborgandersen.restapi.backendservices.PersonService;
import dk.vandborgandersen.restapi.domain.Credentials;
import dk.vandborgandersen.restapi.domain.LoginResponse;
import dk.vandborgandersen.restapi.domain.Person;
import dk.vandborgandersen.restapi.environment.DBFactory;
import org.apache.commons.httpclient.HttpStatus;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.ws.handler.MessageContext;

/**
 * @author Morten Andersen (mortena@gmail.com)
 */
@Path("/auth")
public class AuthRestService {
    private AuthService authService;
    private PersonService personService;

    @GET
    @Path("/hello")
    public Response helloWorld() {
        return Response.status(200).entity("world").build();
    }

    @GET
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginSample() {
        Credentials credentials = new Credentials();
        credentials.setUsername("admin@domain.com");
        credentials.setPassword("password");
        return Response.status(HttpStatus.SC_OK).entity(credentials).build();
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(Credentials credentials) {
        Credentials credsFound = getAuthService().validateCredentials(credentials);
        if (credsFound != null) {
            LoginResponse entity = new LoginResponse();
            entity.setAuthenticationOk(true);
            entity.setUsername(credsFound.getUsername());
            entity.setMessage("Username and password correct.");
            return Response.status(HttpStatus.SC_OK).entity(entity).build();
        }
        LoginResponse entity = new LoginResponse();
        entity.setAuthenticationOk(false);
        entity.setMessage("Email and password was not valid.");
        return Response.status(HttpStatus.SC_UNAUTHORIZED).entity(entity).build();
    }

    @POST
    @Path("/credentials")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(Credentials credentials) {
        credentials = getAuthService().createCredentials(credentials.getUsername(), credentials.getPassword());
        return Response.status(HttpStatus.SC_OK).entity(credentials).build();
    }

    private AuthService getAuthService() {
        if (authService == null) {
            synchronized (this) {
                if (authService == null) {
                    authService = new AuthService(DBFactory.getDataSource());
                }
            }
        }
        return authService;
    }

    private PersonService getPersonService() {
        if (personService == null) {
            synchronized (this) {
                if (personService == null) {
                    personService = new PersonService();
                }
            }
        }
        return personService;
    }
}
