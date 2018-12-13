package project2019.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * Created by jlgaoyuan on 2018/12/11.
 *
 */
//@WebFilter(filterName = "FirstFilter",urlPatterns = {"*.do","*,jsp"})
@WebFilter(filterName = "FirstFilter",urlPatterns = {"/first"})
public class FirstFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("into filter");
        chain.doFilter(request,response);
        System.out.println("out filter");
    }

    @Override
    public void destroy() {

    }
}