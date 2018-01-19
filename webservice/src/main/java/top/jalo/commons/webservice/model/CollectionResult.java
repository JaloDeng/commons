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
 * (1) {"items": [{"id": 1, "name": "JALO"}, {"id": 2, "name": "DENG"}, ...],
 * 		"page": 1, "totalPage": 10, "size": 10, "total": 100, "success": true,
 * 		"empty": false} <br>
 * (2) {"total": 0, "success": true, "empty": true} <br>
 * (3) {"success": false, "empty": true, "message": "..."}
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
		super(true, page.getContent().isEmpty(), null);
		Boolean isPageEmpty = page.getContent().isEmpty();
		this.items = new ArrayList<>(page.getContent());
		this.page = isPageEmpty ? null : page.getNumber() + 1;
		this.totalPage = isPageEmpty ? null : page.getTotalPages();
		this.size = isPageEmpty ? null : page.getNumberOfElements();
		this.total = isPageEmpty ? 0 : page.getTotalElements();
	}

	public CollectionResult(List<T> items) {
		super(true, items.isEmpty(), null);
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
		this.total = null;
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

	public Integer getSize() {
		return size;
	}

	public Long getTotal() {
		return total;
	}
}
