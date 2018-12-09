import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * Created by jlgaoyuan on 2018/12/9.
 *
 */
public class Cservlet extends Bservlet {

    @Override
    public void init()  {
        System.out.println("happy C servlet");
    }
    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
       String value = getInitParameter("p1");
    }
}
