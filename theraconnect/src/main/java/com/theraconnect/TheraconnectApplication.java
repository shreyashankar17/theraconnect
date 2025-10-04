package com.theraconnect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TheraconnectApplication {

	public static void main(String[] args) {
		SpringApplication.run(TheraconnectApplication.class, args);
	}

}
