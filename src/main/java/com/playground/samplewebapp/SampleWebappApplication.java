package com.playground.samplewebapp;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.playground.samplewebapp.repository")
@EnableMongoAuditing
public class SampleWebappApplication {

	public static void main(String[] args) {
		SpringApplication.run(SampleWebappApplication.class, args);
	}

	@Bean
	public Module jodaModule() {
		return new JodaModule();
	}
}
