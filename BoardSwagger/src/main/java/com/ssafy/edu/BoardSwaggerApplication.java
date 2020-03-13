package com.ssafy.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableCaching
@SpringBootApplication
public class BoardSwaggerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardSwaggerApplication.class, args);
	}
	//http://localhost:8197/swagger-ui.html#
	
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
}
