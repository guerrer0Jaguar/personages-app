package org.guerrer0jaguar.personages.backend.repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Base64;

import org.guerrer0jaguar.personages.backend.Config;
import org.guerrer0jaguar.personages.backend.model.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaContext;

import jakarta.persistence.EntityManager;


public class CustomImageRepositoryImpl implements CustomImageRepository {

	private final EntityManager em;
	private final Path baseFolder;
	
	@Autowired
	public CustomImageRepositoryImpl(JpaContext context, Config config) {
		this.em = context.getEntityManagerByManagedType(Image.class);		
		this.baseFolder = Paths.get(config.getPathToSaveImages());
		try {
			Files.createDirectories(baseFolder);
		} catch (IOException e) {
			throw new RuntimeException("Error when trying to create directory to save images", e);
		}
	}

	@Override
	public <S extends Image> S save(S img) {
		
		if ( img == null) {
			return img;
		}
		
		saveOnFileSystem(img);		
		em.persist(img);
		img.setContent("");
		return img;
	}

	private <S extends Image> void saveOnFileSystem(S img){
		
		if(img.getName() == null || img.getContent() == null) {
			return;
		}
				
		try {			
			String base64Image = img.getContent().split(",")[1];
			byte[] imageBytes = Base64.getDecoder().decode(base64Image);
			Path filePath = baseFolder.resolve(img.getName());
			Files.write(filePath, imageBytes,StandardOpenOption.CREATE);
			img.setUrl(filePath.toAbsolutePath().toString());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}