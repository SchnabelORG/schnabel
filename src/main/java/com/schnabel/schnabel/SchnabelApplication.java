package com.schnabel.schnabel;

import java.util.Collections;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
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
