package top.jalo.commons.webservice.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 *
 * @Author JALO
 *
 */
@JsonInclude(Include.NON_EMPTY)
public class Result<T> {

	private List<T> items;
	
	private Integer page;
	
	private Integer totalPage;

	private Integer currentCount;
	
	private Long total;

	private Boolean success;
	
	private String message;

	public Result(Page<T> page) {
		this.items = new ArrayList<>(page.getContent());
		this.page = page.getNumber() + 1;
		this.totalPage = page.getTotalPages();
		this.currentCount = page.getNumberOfElements();
		this.total = page.getTotalElements();
		this.success = !items.isEmpty();
		this.message = null;
	}
	
	public Result(List<T> items) {
		this.items = items;
		this.page = null;
		this.totalPage = null;
		this.currentCount = null;
		this.total = Long.valueOf(items.size());
		this.success = !items.isEmpty();
		this.message = null;
	}
	
	public Result(Exception e) {
		this.items = null;
		this.page = null;
		this.totalPage = null;
		this.currentCount = null;
		this.total = null;
		this.success = false;
		this.message = e.getMessage();
	}
	
	public List<T> getItems() {
		return items;
	}

	public Integer getPage() {
		return page;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public Integer getCurrentCount() {
		return currentCount;
	}

	public Long getTotal() {
		return total;
	}

	public Boolean getSuccess() {
		return success;
	}

	public String getMessage() {
		return message;
	}
}
