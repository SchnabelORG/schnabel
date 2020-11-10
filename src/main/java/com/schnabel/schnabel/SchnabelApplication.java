package com.schnabel.schnabel;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SchnabelApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchnabelApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx)
	{
		return args ->
		{
			System.out.println("Beans provided by SB:");
			String[] names = ctx.getBeanDefinitionNames();
			Arrays.sort(names);
			for(String name : names)
			{
				System.out.println(name);
			}
		};
	}

}
