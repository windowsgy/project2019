import javax.servlet.*;
import java.io.IOException;

/**
 * Created by jlgaoyuan on 2018/12/9.
 *
 */
public class Bservlet implements Servlet {
    private ServletConfig config;

    @Override
    public void destroy() {
        System.out.println("what is this ?");
    }
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        //把tomcat 传递的servletConfig 传递；
        this.config = servletConfig;
    }

    public void init(){

    }

    //会在init方法后被调用
    @Override
    public ServletConfig getServletConfig() {
        return this.config;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        System.out.println("Each processing is invoked");
    }

    @Override
    public String getServletInfo() {
        return "i am happy servlet";
    }

    public ServletContext getServletContext(){
        return config.getServletContext();
    }

    public String getServletName(){
        return config.getServletName();
    }

    public String getInitParameter(String name){
        return config.getInitParameter(name);
    }

}
