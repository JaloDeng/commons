package top.jalo.commons.webservice.exception;

import org.springframework.http.HttpStatus;

/**
 * @author JALO
 *
 */
public class ResourceDependedException extends GenericException {

	private static final long serialVersionUID = 1L;
	
	private final String resource;
	
	public ResourceDependedException(String resource) {
		super(HttpStatus.CONFLICT);
		this.resource = resource;
	}
	
	public ResourceDependedException(String resource, Throwable cause) {
		super(HttpStatus.CONFLICT, cause);
		this.resource = resource;
	}

	public String getMessage() {
		return String.format("Resource [%1$s] depended by other resource(s).", resource);
	}
}
