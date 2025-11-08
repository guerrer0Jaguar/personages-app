package org.guerrer0jaguar.personages.backend;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFound extends RuntimeException {
	
    private static final long serialVersionUID = 5762190767271807475L;
    
	public ResourceNotFound(String message) {
        super(message);
    }
}
