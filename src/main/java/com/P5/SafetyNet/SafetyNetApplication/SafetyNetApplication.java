package com.P5.SafetyNet.SafetyNetApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
@ImportResource("classpath*:SpringConfig.xml")
@SpringBootApplication(scanBasePackages = "com.P5.SafetyNet")
public class SafetyNetApplication {

	public static void main(String[] args) {
		SpringApplication.run(SafetyNetApplication.class, args);
	}

}
