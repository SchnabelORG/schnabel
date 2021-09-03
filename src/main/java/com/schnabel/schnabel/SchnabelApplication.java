package com.schnabel.schnabel;

import java.util.Collections;

import com.schnabel.schnabel.misc.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableScheduling
@SpringBootApplication
@EnableConfigurationProperties({
		FileStorageProperties.class
})
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
				String allowedOrigin = System.getenv().getOrDefault("SCHNABEL_FRONTEND_ADDR", "http://localhost")
				+ ":" + System.getenv().getOrDefault("SCHNABEL_FRONTEND_PORT", "8080");
				registry.addMapping("/**")
				.allowCredentials(true)
				.allowedHeaders("*")
				.allowedOrigins(allowedOrigin)
				.allowedMethods("GET", "PUT", "POST", "DELETE", "OPTIONS");
			}
		};
	}
}
