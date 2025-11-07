package org.guerrer0jaguar.personages.backend.model;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public final class Personage {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	//private Image image;
//	private Media media;
//	private Rol rol;
	private String description;
	private Timestamp dateCreation;
}