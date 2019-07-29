package crawler.filter;

import crawler.entity.Account;
import crawler.entity.Credential;
import crawler.entity.JsonObjApi;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String token = request.getHeader("Authorization");
        if (token != null) {
            Credential credential = ofy().load().type(Credential.class).id(token).now();
            if (credential != null) {
                Account account = ofy().load().type(Account.class).id(credential.getAccountId()).now();
                System.out.println(account.getUsername());
                filterChain.doFilter(request, response);
            } else response.getWriter().println(new JsonObjApi()
                    .setStatus(HttpServletResponse.SC_FORBIDDEN)
                    .setMessage("Unauthorized access!")
                    .toJsonString());
        } else response.getWriter().println(new JsonObjApi()
                .setStatus(HttpServletResponse.SC_FORBIDDEN)
                .setMessage("Unauthorized access!")
                .toJsonString());

    }

    @Override
    public void destroy() {

    }
}
