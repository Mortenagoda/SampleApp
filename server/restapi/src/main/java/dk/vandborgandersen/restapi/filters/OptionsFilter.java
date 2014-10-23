package dk.vandborgandersen.restapi.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Morten Andersen (mortena@gmail.com)
 */
public class OptionsFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        if ("OPTIONS".equals(httpServletRequest.getMethod().toUpperCase())) {
            //TODO: externalize the Allow-Origin
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
            response.addHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept, ApiKey");
            response.addHeader("Access-Control-Max-Age", "1728000");
//        }
        filterChain.doFilter(httpServletRequest, response);
    }

    @Override
    public void destroy() {

    }
}
