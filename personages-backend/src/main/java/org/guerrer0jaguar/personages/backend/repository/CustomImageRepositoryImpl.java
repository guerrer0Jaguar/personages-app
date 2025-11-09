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


public class CustomImageRepositoryImpl<T> implements CustomImageRepository<T> {

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

	@SuppressWarnings("unchecked")
	@Override
	public <S extends T> S save(S ent) {
		Image img = (Image) ent; 
		if ( img == null) {
			return (S)img;
		}
		
		saveOnFileSystem(img);		
		em.persist(img);
		img.setContent("");
		
		return (S)img;
	}

	private void saveOnFileSystem(Image img){
		
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