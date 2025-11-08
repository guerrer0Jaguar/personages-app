package org.guerrer0jaguar.personages.backend;

import java.time.LocalDateTime;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ExceptionHandling {

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorMessage> responseWithBadRequest(IllegalArgumentException ex){
		logError(ex);
		ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), LocalDateTime.now());
		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	}
		
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorMessage> responseWithResourceNotFound(ResourceNotFoundException ex){
		logError(ex);
		ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND.value(),ex.getMessage(),LocalDateTime.now());		
		return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
	}
	
	 @ExceptionHandler({RuntimeException.class, Exception.class})
	    public ResponseEntity<ErrorMessage> handleExceptions(Exception ex, WebRequest request) {
		 logError(ex);
		 ErrorMessage message = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				 "An Internal error ocurred",
				 LocalDateTime.now());
		 
		 return new ResponseEntity<ErrorMessage>(message, HttpStatus.INTERNAL_SERVER_ERROR);  
	 }
	 
	 private void logError( Throwable ex) {
		 log.error(ex.getMessage(), ex);
	 }
}
