package project2019.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * Created by jlgaoyuan on 2018/12/11.
 */
@WebFilter(filterName = "SecondFilter",urlPatterns = {"/second"})
public class SecondFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("into secondFilter");
        chain.doFilter(request,response);
        System.out.println("out secondFilter");
    }

    @Override
    public void destroy() {

    }
}
