package org.guerrer0jaguar.personages.backend.repository;

import org.guerrer0jaguar.personages.backend.model.Image;

public interface CustomImageRepository {
	<S extends Image> S save(S entity);
}
