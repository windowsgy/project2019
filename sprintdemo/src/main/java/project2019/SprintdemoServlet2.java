package project2019;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import project2019.servlet.SecondServlet;

@SpringBootApplication
public class SprintdemoServlet2 {

	public static void main(String[] args) {
		SpringApplication.run(SprintdemoServlet2.class, args);

	}

	/*@Bean
	public ServletRegistrationBean getServleRegistrationBean(){
		ServletRegistrationBean bean = new ServletRegistrationBean(new SecondServlet());
		bean.addUrlMappings("/second");
		return bean;
	}*/
}
