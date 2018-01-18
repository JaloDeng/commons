package top.jalo.commons.webservice.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Collection result. <br>
 * 
 * Example : <br>
 * (1) {"items": [{"id": 1, "name": "JALO"}, {"id": 2, "name": "DENG"}, ...], "page": 1, "totalPage": 10, "size": 10, "total": 100, "message": true} <br>
 * (2) {"page": 0, "totalPage": 0, "size": 0, "total": 0, "success": false, "message": "..."}
 *
 * @Author JALO
 */
@JsonInclude(Include.NON_EMPTY)
public class CollectionResult<T> extends Result<T> {

	private List<T> items;
	
	private Integer page;
	
	private Integer totalPage;

	private Integer size;
	
	private Long total;
	
	public CollectionResult(Page<T> page) {
		super(!page.getContent().isEmpty(), null);
		this.items = new ArrayList<>(page.getContent());
		this.page = page.getContent().isEmpty() ? 0 : page.getNumber() + 1;
		this.totalPage = page.getTotalPages();
		this.size = page.getNumberOfElements();
		this.total = page.getTotalElements();
	}
	
	public CollectionResult(List<T> items) {
		super(!items.isEmpty(), null);
		this.items = items;
		this.page = null;
		this.totalPage = null;
		this.size = null;
		this.total = Long.valueOf(items.size());
	}
	
	public CollectionResult(Exception e) {
		super(e);
		this.items = null;
		this.page = null;
		this.totalPage = null;
		this.size = null;
		this.total = 0L;
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
		return size;
	}

	public Long getTotal() {
		return total;
	}
}
