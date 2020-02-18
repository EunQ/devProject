package com.ssafy.edu.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer  {

        private String urlPath;

        public WebConfig() {
                this.urlPath = "file:///home/ubuntu/image/";
        }

        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
                System.out.println("----------------------------------------------");
                registry.addResourceHandler("/image/**").addResourceLocations(urlPath).setCachePeriod(20);
        }
        
        @Override
    	public void addCorsMappings(CorsRegistry registry) {
    		registry.addMapping("/**")
    		.allowedOrigins("*")
    		.allowedMethods("*");
    	}
}
