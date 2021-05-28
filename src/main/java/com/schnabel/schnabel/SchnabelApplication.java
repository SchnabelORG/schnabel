package com.schnabel.schnabel;

import java.util.Collections;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableScheduling
@SpringBootApplication
public class SchnabelApplication {

	public static void main(String[] args) {
		// NOTE(Jovan): Change default port to 8082 and run
		SpringApplication app = new SpringApplication(SchnabelApplication.class);
        app.setDefaultProperties(Collections
          .singletonMap("server.port", "${custom.port}"));
        app.run(args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer(){
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
				.allowCredentials(true)
				.allowedHeaders("*")
				.allowedOrigins("http://localhost:8080")
				.allowedMethods("GET", "PUT", "POST", "DELETE", "OPTIONS");
			}
		};
	}
}
