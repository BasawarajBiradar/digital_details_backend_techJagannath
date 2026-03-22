package com.techjagannath.digital_identification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
public class DigitalIdentificationApplication {

	public static void main(String[] args) {
		SpringApplication.run(DigitalIdentificationApplication.class, args);
	}

}
