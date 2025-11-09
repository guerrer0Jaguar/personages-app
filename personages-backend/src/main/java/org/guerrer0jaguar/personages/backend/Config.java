package org.guerrer0jaguar.personages.backend;

import org.guerrer0jaguar.personages.backend.model.MediaType;
import org.guerrer0jaguar.personages.backend.repository.MediaTypeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
@ConfigurationProperties(prefix = "personages-backend")
@Getter
@Setter
public class Config {

	private String pathToSaveImages = "tmp";
	/*
	@Bean
	CommandLineRunner initDatabase(MediaTypeRepository repository) {
		return args -> {
			log.info("Loading MediaType catalog...");
			repository.save(new MediaType(1L, "Película"));
			repository.save(new MediaType(2L, "Serie"));
			log.info("Default catalog of Media Type loaded");
		};		
	}	
	*/
}