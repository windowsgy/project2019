package project2019;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import project2019.servlet.SecondServlet;

@SpringBootApplication
@ServletComponentScan
public class SprintFirsterFilter {

	public static void main(String[] args) {
		SpringApplication.run(SprintFirsterFilter.class, args);

	}


}
