package com.schnabel.schnabel;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SchnabelApplication {

	public static void main(String[] args) {
		// NOTE(Jovan): Change default port to 8082 and run
		SpringApplication app = new SpringApplication(SchnabelApplication.class);
        app.setDefaultProperties(Collections
          .singletonMap("server.port", "8082"));
        app.run(args);
	}

}
