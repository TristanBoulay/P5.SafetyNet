package com.P5.SafetyNet;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.IOException;

@SpringBootApplication
public class SafetyNetApplication {
	public static void main(String[] args) throws IOException {
		SpringApplication.run(SafetyNetApplication.class, args);

	}

}
