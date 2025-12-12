package com.dosemate.Dosemate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DosemateApplication {

	public static void main(String[] args) {
		SpringApplication.run(DosemateApplication.class, args);
	}

}
