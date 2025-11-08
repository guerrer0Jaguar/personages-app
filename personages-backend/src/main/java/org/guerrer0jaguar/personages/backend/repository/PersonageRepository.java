package org.guerrer0jaguar.personages.backend.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.guerrer0jaguar.personages.backend.model.Personage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PersonageRepository extends PagingAndSortingRepository<Personage, Long>,
JpaRepository<Personage, Long>{

	List<Personage> findByNameContainingIgnoreCase(String name);
	List<Personage> findByCreationDateBetween(LocalDateTime init, LocalDateTime end);
}