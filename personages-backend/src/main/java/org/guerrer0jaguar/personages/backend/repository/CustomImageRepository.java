package org.guerrer0jaguar.personages.backend.repository;

public interface CustomImageRepository<T> {
	<S extends T> S save(S entity);
}
