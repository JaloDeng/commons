package top.jalo.commons.webservice.exception;

import org.springframework.http.HttpStatus;

/**
 * @author JALO
 *
 */
public class ResourceNoPrivilegeException extends GenericException {

	private static final long serialVersionUID = 1L;
	
	private final String resource;
	
	public ResourceNoPrivilegeException(String resource) {
		super(HttpStatus.FORBIDDEN);
		this.resource = resource;
	}
	
	public ResourceNoPrivilegeException(String resource, Throwable cause) {
		super(HttpStatus.FORBIDDEN, cause);
		this.resource = resource;
	}

	public String getMessage() {
		return String.format("Resource [%1$s] has not access privilege.", resource);
	}
}
