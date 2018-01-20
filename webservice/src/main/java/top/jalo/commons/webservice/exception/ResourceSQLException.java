package top.jalo.commons.webservice.exception;

import org.springframework.http.HttpStatus;

/**
 * @author JALO
 *
 */
public class ResourceSQLException extends GenericException {

	private static final long serialVersionUID = 1L;
	
	private final Integer errorCode;
	private final String SQLState;
	
	public ResourceSQLException(Integer errorCode, String SQLState, String message) {
		super(HttpStatus.CONFLICT, message);
		this.errorCode = errorCode;
		this.SQLState = SQLState;
	}

	public Boolean getSuccess() {
		return false;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public String getSQLState() {
		return SQLState;
	}
}
