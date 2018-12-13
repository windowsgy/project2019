package project2019;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan//在springboot启动时会扫描webservlet注解
public class SprintdemoServlet {

	public static void main(String[] args) {
		SpringApplication.run(SprintdemoServlet.class, args);

	}
}
