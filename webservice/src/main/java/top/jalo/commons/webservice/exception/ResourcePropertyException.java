package top.jalo.commons.webservice.exception;

import org.springframework.http.HttpStatus;

/**
 * @author JALO
 *
 */
public class ResourcePropertyException extends GenericException {

	private static final long serialVersionUID = 1L;
	
	private final String property;
	
	public ResourcePropertyException(String property) {
		super(HttpStatus.BAD_REQUEST);
		this.property = property;
	}
	
	public ResourcePropertyException(String property, Throwable cause) {
		super(HttpStatus.BAD_REQUEST, cause);
		this.property = property;
	}

	public String getMessage() {
		return String.format("Resource property [%1$s] is invalid.", property);
	}
}
