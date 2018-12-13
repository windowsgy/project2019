package project2019;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import project2019.filter.SecondFilter;
import project2019.servlet.SecondServlet;

@SpringBootApplication
public class SprintSecondFilter {

	public static void main(String[] args) {
		SpringApplication.run(SprintSecondFilter.class, args);
	}

	@Bean
	public ServletRegistrationBean getServleRegistrationBean(){
		ServletRegistrationBean bean = new ServletRegistrationBean(new SecondServlet());
		bean.addUrlMappings("/second");
		return bean;
	}

	@Bean
	public FilterRegistrationBean getFilterRegistrationBean(){
		FilterRegistrationBean bean = new FilterRegistrationBean(new SecondFilter());
		bean.addUrlPatterns("/second");
		return bean;
	}
}
