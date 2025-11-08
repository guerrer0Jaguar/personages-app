package org.guerrer0jaguar.personages.backend;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ErrorMessage {
	private final int status;
	private final String message;
	private final LocalDateTime timestamp;
	
	public ErrorMessage(int status, String message, LocalDateTime timestamp) {
		super();
		this.status = status;
		this.message = message;
		this.timestamp = timestamp;
	}		
}
