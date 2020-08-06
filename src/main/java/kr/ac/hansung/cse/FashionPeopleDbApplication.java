package kr.ac.hansung.cse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class FashionPeopleDbApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(FashionPeopleDbApplication.class, args);
	}

}
