package crawler.filter;

import com.googlecode.objectify.ObjectifyService;
import crawler.entity.Article;
import crawler.entity.Category;


import javax.servlet.*;
import java.io.IOException;

public class MyObjectifyFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ObjectifyService.register(Article.class);
        ObjectifyService.register(Category.class);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void destroy() {

    }
}
