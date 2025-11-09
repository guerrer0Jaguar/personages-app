package org.guerrer0jaguar.personages.backend.repository;

import org.guerrer0jaguar.personages.backend.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ImageRepository extends JpaRepository<Image, Long>, 
	PagingAndSortingRepository<Image,Long>,
	CustomImageRepository<Image>{
}