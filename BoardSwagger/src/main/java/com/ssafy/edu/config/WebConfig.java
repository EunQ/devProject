package com.ssafy.edu.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer  {

	private String urlPath;
	
	public WebConfig() {
		this.urlPath = "file:///C:/uploadFiles/";
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		System.out.println("----------------------------------------------");
		registry.addResourceHandler("/images/**").addResourceLocations(urlPath).setCachePeriod(20);
	}
}
