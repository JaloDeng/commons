package top.jalo.commons.webservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Base result. <br>
 * 
 * Example : <br>
 * (1) {"item": {"id":1, "name": JALO, ...}, "success": true, "empty": false} <br>
 * (2) {"success": true, "empty": true}
 * (3) {"success": false, "empty": true, "message": "..."}
 * 
 * @author JALO
 */
@JsonInclude(Include.NON_EMPTY)
public class Result<T> {

	private T item;
	
	private Boolean success;
	
	private Boolean isEmpty;
	
	private String message;
	
	protected Result() {}
	
	public Result(T item) {
		this.item = item;
		this.success = true;
		this.isEmpty = item == null ? true : false;
		this.message = null;
	}
	
	public Result(Boolean success, Boolean isEmpty, String message) {
		this.item = null;
		this.success = success;
		this.isEmpty = isEmpty;
		this.message = message;
	}
	
	public Result(Exception e) {
		this.item = null;
		this.success = false;
		this.isEmpty = true;
		this.message = e.getMessage();
	}
	
	public T getItem() {
		return item;
	}

	public Boolean getSuccess() {
		return success;
	}

	public Boolean isEmpty() {
		return isEmpty;
	}

	public String getMessage() {
		return message;
	}
}
