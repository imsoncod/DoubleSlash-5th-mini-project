package com.doubleslash.mini;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class DoubleSlash5thApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(DoubleSlash5thApplication.class, args);
	}

	@Override protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) { 
		
		return builder.sources(DoubleSlash5thApplication.class); 
		
	}

}
