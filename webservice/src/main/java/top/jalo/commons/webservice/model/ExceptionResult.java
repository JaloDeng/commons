package top.jalo.commons.webservice.model;

/**
 * @author JALO
 *
 */
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
