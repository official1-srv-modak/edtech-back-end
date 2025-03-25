package com.modakdev.modakflix_stream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.modakdev.models.repo")
@EntityScan(basePackages = "com.modakdev.models")
public class ModakflixStreamApplication {

	public static void main(String[] args) {
		SpringApplication.run(ModakflixStreamApplication.class, args);
	}

}
