package dk.vandborgandersen.restapi.restservices;

import dk.vandborgandersen.restapi.backendservices.ApiKeysService;
import dk.vandborgandersen.restapi.domain.ApiKey;
import dk.vandborgandersen.restapi.environment.DBFactory;
import org.apache.commons.httpclient.HttpStatus;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

/**
 * @author Morten Andersen (mortena@gmail.com)
 */
@Path("/api")
public class ApiRestService {
    private static final Logger LOGGER = Logger.getLogger(ApiRestService.class.getName());

    ApiKeysService apiKeysService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/apikeys")
    public Response getApiKey() {
        ApiKey apiKey = getApiKeysService().getApiKey();
        return Response.status(HttpStatus.SC_OK).entity(apiKey).build();
    }

    private ApiKeysService getApiKeysService() {
        if (apiKeysService == null) {
            apiKeysService = getApiKeysServiceSync();
        }
        return apiKeysService;
    }

    private synchronized ApiKeysService getApiKeysServiceSync() {
        return new ApiKeysService(DBFactory.getDataSource());
    }
}
