package top.jalo.commons.webservice.exception;

import org.springframework.http.HttpStatus;

/**
 * @author JALO
 *
 */
public class ResourceNotFoundException extends GenericException {

	private static final long serialVersionUID = 1L;
	
	private final String resource;
	
	public ResourceNotFoundException(String resource) {
		super(HttpStatus.NOT_FOUND);
		this.resource = resource;
	}
	
	public ResourceNotFoundException(String resource, Throwable cause) {
		super(HttpStatus.NOT_FOUND, cause);
		this.resource = resource;
	}

	public String getMessage() {
		return String.format("Resource [%1$s] is not found.", resource);
	}
}
