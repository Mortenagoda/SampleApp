package dk.vandborgandersen.restapi.filters;

import dk.vandborgandersen.restapi.backendservices.ApiKeysService;
import dk.vandborgandersen.restapi.environment.DBFactory;
import org.apache.commons.httpclient.HttpStatus;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * @author Morten Andersen (mortena@gmail.com)
 */
public class ApiSecurityFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(ApiSecurityFilter.class.getName());
    private ApiKeysService apiKeysService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        apiKeysService = new ApiKeysService(DBFactory.getDataSource());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        if (!httpServletRequest.getRequestURI().endsWith("/apikeys")) {
            String apikey = httpServletRequest.getHeader("ApiKey");
            LOGGER.info("ApiKey used: " + apikey);
            if (!apiKeysService.validateApiKey(apikey)) {
                httpServletResponse.setStatus(HttpStatus.SC_UNAUTHORIZED);
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
