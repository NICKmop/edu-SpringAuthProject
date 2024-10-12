package com.cspro.edu_SpringAuthProject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsMvcConfig implements WebMvcConfigurer {
	
    @Override
    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .exposedHeaders("Set-Cookie")
//                .allowedOrigins("http://localhost:8080");
    	
        registry.addMapping("/**")
	        .allowedOrigins("*") // Or specific origins
	        .allowedMethods("GET", "POST", "PUT", "DELETE")
	        .allowedHeaders("*")
	        .exposedHeaders("access"); // Expose access header
        
        
//    	registry.addMapping("/**")
//		        .allowedOrigins("*") // or specific origin
//		        .allowedMethods("*")
//		        .allowedHeaders("*")
//		        .exposedHeaders("access"); // Expose the access header
		}
}
