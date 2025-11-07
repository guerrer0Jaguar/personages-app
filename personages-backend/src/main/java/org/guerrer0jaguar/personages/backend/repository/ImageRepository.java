package org.guerrer0jaguar.personages.backend.repository;

import org.guerrer0jaguar.personages.backend.model.Image;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "image", path = "image")
public interface ImageRepository extends CrudRepository<Image, Long>, 
	PagingAndSortingRepository<Image,Long> {

}