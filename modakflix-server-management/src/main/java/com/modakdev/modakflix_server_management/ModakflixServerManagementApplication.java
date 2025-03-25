package com.modakdev.modakflix_server_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.modakdev.models.repo")
@EnableScheduling
@EntityScan(basePackages = "com.modakdev.models")
public class ModakflixServerManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(ModakflixServerManagementApplication.class, args);
	}

}
