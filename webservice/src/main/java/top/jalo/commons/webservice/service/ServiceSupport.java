package top.jalo.commons.webservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import top.jalo.commons.webservice.model.Sorter;

/**
 * Support
 * 
 * @Author JALO
 *
 */
public class ServiceSupport {

	/**
	 * Get Pageable.
	 * 
	 * @param page
	 * @param size
	 * @param sorters
	 * @return PageRequest
	 */
	public static PageRequest createPageRequest(Integer page, Integer size, List<Sorter> sorters) {
		page = (page == null || page < 0) ? 0 : page;
		size = (size == null || size <= 0) ? 10 : size;
		Sort sort = null;
		
		if (sorters != null && !sorters.isEmpty()) {
			sort = new Sort(sorters.stream().map(sorter -> {
				return new Sort.Order(
						sorter.getDirection() == null ? null : Sort.Direction.valueOf(sorter.getDirection().name()),
						sorter.getProperty());
			}).collect(Collectors.toList()));
		}
		return new PageRequest(page, size, sort);
	}
}
