package top.jalo.commons.webservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author JALO
 *
 */
@JsonInclude(Include.NON_EMPTY)
public class ExceptionResult {

	private final int status;
	private final Boolean success;
	private final String message;
	
	public ExceptionResult(int status) {
		this.status = status;
		this.success = false;
		this.message = null;
	}
	
	public ExceptionResult(int status, Boolean success, String message) {
		this.status = status;
		this.success = success;
		this.message = message;
	}

	public int getStatus() {
		return status;
	}
	
	public Boolean getSuccess() {
		return success;
	}
	
	public String getMessage() {
		return message;
	}
}
