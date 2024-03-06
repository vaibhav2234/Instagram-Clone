package com.instagram_clone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.instagram_clone.Playloads.ImageDto;

@SpringBootApplication

@EntityScan(basePackages = {"com.instagram_clone.model"})
public class InstagramCloneApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(InstagramCloneApplication.class, args);
	}
	 
	@Bean
	WebMvcConfigurer mvcConfigurer()
	{
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				// TODO Auto-generated method stub
				registry.addMapping("/**").allowedOrigins("http://localhost:8082");
			}
		};
		
	}

}
