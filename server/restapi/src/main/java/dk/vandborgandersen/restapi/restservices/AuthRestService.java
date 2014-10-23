package dk.vandborgandersen.restapi.restservices;

import dk.vandborgandersen.restapi.backendservices.AuthService;
import dk.vandborgandersen.restapi.backendservices.PersonService;
import dk.vandborgandersen.restapi.domain.EmailPassword;
import dk.vandborgandersen.restapi.domain.LoginResponse;
import dk.vandborgandersen.restapi.domain.Person;
import dk.vandborgandersen.restapi.environment.DBFactory;
import org.apache.commons.httpclient.HttpStatus;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
        EmailPassword emailPassword = new EmailPassword();
        emailPassword.setEmail("admin@domain.com");
        emailPassword.setPassword("password");
        return Response.status(HttpStatus.SC_OK).entity(emailPassword).build();
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(EmailPassword emailPassword) {
        if (getAuthService().validateEmailPassword(emailPassword.getEmail(), emailPassword.getPassword())) {
            Person personByEmail = getPersonService().findPersonByEmail(emailPassword.getEmail());
            LoginResponse entity = new LoginResponse();
            entity.setAuthenticationOk(true);
            entity.setPerson(personByEmail);
            entity.setMessage("Email and password correct.");
            return Response.status(HttpStatus.SC_OK).entity(entity).build();
        }
        LoginResponse entity = new LoginResponse();
        entity.setAuthenticationOk(false);
        entity.setMessage("Email and password was not valid.");
        return Response.status(HttpStatus.SC_UNAUTHORIZED).entity(entity).build();
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
