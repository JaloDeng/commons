package top.jalo.commons.webservice.exception;

import org.springframework.http.HttpStatus;

/**
 * @author JALO
 *
 */
public class HttpStatusException extends Exception {

	private static final long serialVersionUID = 1L;

	private final int status;
	
	public HttpStatusException() {
		super();
		this.status = HttpStatus.INTERNAL_SERVER_ERROR.value();
	}
	
	public HttpStatusException(String message) {
		super(message);
		this.status = HttpStatus.INTERNAL_SERVER_ERROR.value(); 
	}
	
	public HttpStatusException(Throwable cause) {
		super(cause);
		this.status = HttpStatus.INTERNAL_SERVER_ERROR.value(); 
	}
	
	public HttpStatusException(String message, Throwable cause) {
		super(message, cause);
		this.status = HttpStatus.INTERNAL_SERVER_ERROR.value(); 
	}
	
	public HttpStatusException(int statusCodeValue) {
		super();
		this.status = statusCodeValue;
	}
	
	public HttpStatusException(int statusCodeValue, String message) {
		super(message);
		this.status = statusCodeValue;
	}
	
	public HttpStatusException(int statusCodeValue, Throwable cause) {
		super(cause);
		this.status = statusCodeValue;
	}
	
	public HttpStatusException(int statusCodeValue, String message, Throwable cause) {
		super(message, cause);
		this.status = statusCodeValue;
	}
	
	public HttpStatusException(HttpStatus statusCode) {
		super();
		this.status = statusCode.value();
	}
	
	public HttpStatusException(HttpStatus statusCode, String message) {
		super(message);
		this.status = statusCode.value();
	}
	
	public HttpStatusException(HttpStatus statusCode, Throwable cause) {
		super(cause);
		this.status = statusCode.value();
	}
	
	public HttpStatusException(HttpStatus statusCode, String message, Throwable cause) {
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
