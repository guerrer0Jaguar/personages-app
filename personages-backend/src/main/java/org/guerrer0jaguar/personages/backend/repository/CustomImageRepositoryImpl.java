package org.guerrer0jaguar.personages.backend.repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Base64;

import org.guerrer0jaguar.personages.backend.model.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaContext;

import jakarta.persistence.EntityManager;


public class CustomImageRepositoryImpl implements CustomImageRepository {

	private final EntityManager em;
	
	@Autowired
	public CustomImageRepositoryImpl(JpaContext context) {
		this.em = context.getEntityManagerByManagedType(Image.class);
	}

	@Override
	public <S extends Image> S save(S img) {
		saveOnFileSystem(img);
		em.persist(img);
		return img;
	}

	private <S extends Image> void saveOnFileSystem(S img){
		
		if(img.getName() == null || img.getContent() == null) {
			return;
		}
		
		Path filePath = Paths.get(img.getName());
		
		try {
			
			String base64Image = img.getContent().split(",")[1];
			byte[] imageBytes = Base64.getDecoder().decode(base64Image);
			Files.write(filePath, imageBytes,StandardOpenOption.CREATE);
			img.setUrl(filePath.toAbsolutePath().toString());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}