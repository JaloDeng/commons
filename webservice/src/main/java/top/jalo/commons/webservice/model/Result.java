package top.jalo.commons.webservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Base result. <br>
 * 
 * Example : <br>
 * (1) {"item": {"id":1, "name": JALO, ...}, "success": true} <br>
 * (2) {"success": false, "message": "..."}
 * 
 * @author JALO
 */
@JsonInclude(Include.NON_EMPTY)
public class Result<T> {

	private T item;
	
	private Boolean success;
	
	private String message;
	
	protected Result() {}
	
	public Result(T item) {
		this.item = item;
		this.success = item == null ? false : true;
		this.message = null;
	}
	
	public Result(Boolean success, String message) {
		this.item = null;
		this.success = success;
		this.message = message;
	}
	
	public Result(Exception e) {
		this.item = null;
		this.success = false;
		this.message = e.getMessage();
	}
	
	public T getItem() {
		return item;
	}

	public Boolean getSuccess() {
		return success;
	}

	public String getMessage() {
		return message;
	}
}
