package org.guerrer0jaguar.personages.backend.repository;

import java.util.List;

import org.guerrer0jaguar.personages.backend.model.Personage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "personage", path = "personage")
public interface PersonageRepository extends PagingAndSortingRepository<Personage, Long>,
CrudRepository<Personage, Long>{

	List<Personage> findByName(@Param("name") String name);
}
