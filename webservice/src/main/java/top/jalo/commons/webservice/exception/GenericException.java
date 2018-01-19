package top.jalo.commons.webservice.exception;

import org.springframework.http.HttpStatus;

/**
 * @author JALO
 *
 */
public class GenericException extends Exception {

	private static final long serialVersionUID = 1L;

	private final int status;
	
	public GenericException() {
		super();
		this.status = HttpStatus.INTERNAL_SERVER_ERROR.value();
	}
	
	public GenericException(String message) {
		super(message);
		this.status = HttpStatus.INTERNAL_SERVER_ERROR.value(); 
	}
	
	public GenericException(Throwable cause) {
		super(cause);
		this.status = HttpStatus.INTERNAL_SERVER_ERROR.value(); 
	}
	
	public GenericException(String message, Throwable cause) {
		super(message, cause);
		this.status = HttpStatus.INTERNAL_SERVER_ERROR.value(); 
	}
	
	public GenericException(int statusCodeValue) {
		super();
		this.status = statusCodeValue;
	}
	
	public GenericException(int statusCodeValue, String message) {
		super(message);
		this.status = statusCodeValue;
	}
	
	public GenericException(int statusCodeValue, Throwable cause) {
		super(cause);
		this.status = statusCodeValue;
	}
	
	public GenericException(int statusCodeValue, String message, Throwable cause) {
		super(message, cause);
		this.status = statusCodeValue;
	}
	
	public GenericException(HttpStatus statusCode) {
		super();
		this.status = statusCode.value();
	}
	
	public GenericException(HttpStatus statusCode, String message) {
		super(message);
		this.status = statusCode.value();
	}
	
	public GenericException(HttpStatus statusCode, Throwable cause) {
		super(cause);
		this.status = statusCode.value();
	}
	
	public GenericException(HttpStatus statusCode, String message, Throwable cause) {
		super(message, cause);
		this.status = statusCode.value();
	}
	
	public HttpStatus getStatusCode() {
		return HttpStatus.valueOf(status);
	}
	
	public int getStatusCodeValue() {
		return status;
	}

}
