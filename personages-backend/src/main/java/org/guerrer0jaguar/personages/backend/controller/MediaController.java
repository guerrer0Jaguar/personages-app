package org.guerrer0jaguar.personages.backend.controller;

import java.util.List;

import org.guerrer0jaguar.personages.backend.model.Media;
import org.guerrer0jaguar.personages.backend.repository.ImageRepository;
import org.guerrer0jaguar.personages.backend.repository.MediaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MediaController {

	private final MediaRepository repository;
	private final ImageRepository imageRepo;
	
	
	public MediaController(MediaRepository repository,
			ImageRepository imageRepo) {
		this.repository = repository;
		this.imageRepo = imageRepo;
	}

	@GetMapping("/media")
	List<Media> all() {
		return repository.findAll();
	}
	
	@PostMapping("/media")
	Media newMedia(@RequestBody Media newMedia) {
		imageRepo.save(newMedia.getImage());
		return repository.save(newMedia);
	}
}
