package top.jalo.commons.webservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import top.jalo.commons.webservice.exception.HttpStatusException;
import top.jalo.commons.webservice.exception.ResourceNotFoundException;

/**
 * @author JALO
 *
 */
//@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

	//@ExceptionHandler(HttpStatusException.class)
	public ResponseEntity<String> handleHttpStatusException(HttpStatusException e) {
		return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
	}
	
	//@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException e) {
		return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
	}
}
