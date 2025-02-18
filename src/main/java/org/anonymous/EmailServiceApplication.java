package org.anonymous;

import com.netflix.discovery.EurekaClient;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class EmailServiceApplication {
	@Autowired
	private EurekaClient eurekaClient;

	@PreDestroy
	public void unregister() {
		eurekaClient.shutdown();
	}
	public static void main(String[] args) {
		SpringApplication.run(EmailServiceApplication.class, args);
	}

}
