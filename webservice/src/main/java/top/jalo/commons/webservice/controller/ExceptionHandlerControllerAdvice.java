package top.jalo.commons.webservice.controller;

import java.sql.SQLException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import top.jalo.commons.webservice.exception.GenericException;
import top.jalo.commons.webservice.model.ExceptionResult;
import top.jalo.commons.webservice.model.SQLExceptionResult;

/**
 * @author JALO
 *
 */
@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

	@ExceptionHandler(GenericException.class)
	public ResponseEntity<ExceptionResult> handleHttpStatusException(GenericException e) {
		return ResponseEntity.status(e.getStatusCode()).body(new ExceptionResult(e.getStatusCodeValue(), false, e.getMessage()));
	}
	
	@ExceptionHandler(SQLException.class)
	public ResponseEntity<SQLExceptionResult> handleSQLException(SQLException e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new SQLExceptionResult(e));
	}
}
