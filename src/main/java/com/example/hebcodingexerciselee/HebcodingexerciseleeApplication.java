package com.example.hebcodingexerciselee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class HebcodingexerciseleeApplication {

	public static void main(String[] args) {
		SpringApplication.run(HebcodingexerciseleeApplication.class, args);
	}
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/images").allowedOrigins("http://localhost:4200");
				registry.addMapping("/images/**").allowedOrigins("http://localhost:4200");
			}
		};
	}
}
