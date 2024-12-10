package com.creating_your_own_time_service;

import com.creating_your_own_time_service.service.TimeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CreatingYourOwnTimeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CreatingYourOwnTimeServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner init(TimeService timeService) {
		return args -> {
			System.out.println(timeService.getOwnTime());
		};
	}

}
