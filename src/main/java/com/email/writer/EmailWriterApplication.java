package com.email.writer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.CrossOrigin;

@EnableScheduling
@SpringBootApplication
public class EmailWriterApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmailWriterApplication.class, args);
	}

}
