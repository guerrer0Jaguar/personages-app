package org.guerrer0jaguar.personages.backend.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public final class Personage {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String name;
	
	private String rol;
	
	private String description;
	
	private java.time.LocalDateTime creationDate;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "media_id", referencedColumnName = "id")
	private Media media;
	
	@OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	@JoinColumn(name = "image_id", referencedColumnName = "id")
	private Image image;
	
	Personage(){ }
}