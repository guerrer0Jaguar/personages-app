package org.guerrer0jaguar.personages.backend.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.guerrer0jaguar.personages.backend.ResourceNotFound;
import org.guerrer0jaguar.personages.backend.model.Personage;
import org.guerrer0jaguar.personages.backend.repository.ImageRepository;
import org.guerrer0jaguar.personages.backend.repository.PersonageRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonageController {
	
	private final PersonageRepository repository;
	private final ImageRepository imageRepo;

	public PersonageController(PersonageRepository repository,
			ImageRepository imageRepo) {
		this.repository = repository;
		this.imageRepo = imageRepo;
	}
	
	@GetMapping("/personage/{id}")
	Personage findById(@PathVariable Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFound("The personage with id:" + id + " doesnt' exits"));
	}
	
	@GetMapping("/personage")
	List<Personage> findByName(@RequestParam(value = "name", defaultValue = "") String name ) {
		
		if ( name.isBlank()) {
			return repository.findAll();
		}
		
		return repository.findByNameContainingIgnoreCase(name);
	}
	
	@PostMapping("/personage")
	Personage newPersonage(@RequestBody Personage newPersonage) {
		imageRepo.save(newPersonage.getImage());
		newPersonage.setDateCreation(new Timestamp(new Date().getTime()));
		return repository.save(newPersonage);
	}
}
