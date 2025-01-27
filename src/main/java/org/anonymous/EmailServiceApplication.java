package org.anonymous;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class EmailServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(EmailServiceApplication.class, args);
	}

}
