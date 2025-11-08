package org.guerrer0jaguar.personages.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public final class MediaType {
	@Id	
	private Long id;
	private String name;
	
	public MediaType() { }
	
	public MediaType(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

}