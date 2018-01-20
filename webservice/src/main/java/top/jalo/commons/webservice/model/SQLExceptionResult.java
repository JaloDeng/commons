package top.jalo.commons.webservice.model;

import java.sql.SQLException;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author JALO
 *
 */
@JsonInclude(Include.NON_EMPTY)
public class SQLExceptionResult extends ExceptionResult {

	private final Integer errorCode;
	
	private final String SQLState;
	
	public SQLExceptionResult(SQLException e) {
		super(HttpStatus.INTERNAL_SERVER_ERROR.value(), false, e.getMessage());
		this.errorCode = e.getErrorCode();
		this.SQLState = e.getSQLState();
	}
	
	public Integer getErrorCode() {
		return errorCode;
	}

	public String getSQLState() {
		return SQLState;
	}

}
