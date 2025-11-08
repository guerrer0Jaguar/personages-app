package org.guerrer0jaguar.personages.backend.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.guerrer0jaguar.personages.backend.model.Personage;
import org.guerrer0jaguar.personages.backend.repository.ImageRepository;
import org.guerrer0jaguar.personages.backend.repository.PersonageRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonageController {
	
	private static final DateTimeFormatter DATE_CREATION_INPUT_FORMAT =  DateTimeFormatter.ISO_LOCAL_DATE;
	
	private final PersonageRepository repository;
	private final ImageRepository imageRepo;	

	public PersonageController(PersonageRepository repository,
			ImageRepository imageRepo) {
		this.repository = repository;
		this.imageRepo = imageRepo;
	}
	
	@PostMapping("/personage")
	Personage newPersonage(@RequestBody Personage newPersonage) {
		imageRepo.save(newPersonage.getImage());		
		newPersonage.setCreationDate(LocalDateTime.now());
		return repository.save(newPersonage);
	}
	
	@PutMapping("/personage/{id}")
	Personage update(@RequestBody Personage updated, @PathVariable Long id) {
		return repository.findById(id)
				.map(personage -> copyAndSavePersonage(updated, personage))
				.orElseGet(()-> {
					return newPersonage(updated);					
				});
	}

	private Personage copyAndSavePersonage(Personage updated, Personage personage) {
		personage.setDescription(updated.getDescription());
		personage.setName(updated.getName());
		personage.setRol(updated.getRol());
		personage.setMedia(updated.getMedia());
		personage.setImage(updated.getImage());
		
		return newPersonage(personage);
	}
	
	@GetMapping("/personage/{id}")
	Personage findById(@PathVariable Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("The personage with id:" + id + " doesnt' exits"));
	}
	
	@GetMapping("/personage")
	List<Personage> find(
			@RequestParam(value = "name", defaultValue = "") String name, 
			@RequestParam(value = "initDate", defaultValue = "") String initDate,
			@RequestParam(value = "endDate", defaultValue = "") String endDate) {		
		
		if ( !name.isBlank()) {
			return repository.findByNameContainingIgnoreCaseOrderByNameAsc(name);
		}
		
		if ( !initDate.isBlank() && !endDate.isBlank()) {
			return findByDateCreation(initDate, endDate);
		}
		
		return repository.findAll();
	}
		
	private List<Personage> findByDateCreation(String initParam, 
			String endParam) {
		
		LocalDateTime init = null;
		LocalDateTime end = null;
		
		try {
			init = LocalDate.parse(initParam, DATE_CREATION_INPUT_FORMAT).atStartOfDay();
			end = LocalDate.parse(endParam, DATE_CREATION_INPUT_FORMAT).atTime(LocalTime.MAX);
		} catch (DateTimeParseException e) {
			throw new IllegalArgumentException("The format date must be yyyy-MM-dd", e);
		}
		
		return repository.findByCreationDateBetweenOrderByCreationDateAsc(init, end);		
	}
	
	@DeleteMapping("/personage/{id}")
	void delete(@PathVariable Long id) {
		repository.deleteById(id);
	}
}