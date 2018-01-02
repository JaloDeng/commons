package top.jalo.commons.search.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import top.jalo.commons.search.entity.ElasticSearchTestEntity;
import top.jalo.commons.search.repository.ElasticSearchTestRepository;

/**
 * Service : Test ElasticSearch
 * 
 * @Author JALO
 *
 */
@Service
public class ElasticSearchTestService {

	@Autowired
	private ElasticSearchTestRepository elasticSearchTestRepository;
	
	/**
	 * Initialize data
	 * 
	 * @return List<ElasticSearchTestEntity>
	 */
	public List<ElasticSearchTestEntity> initData() {
		elasticSearchTestRepository.deleteAll();
		
		elasticSearchTestRepository.save(new ElasticSearchTestEntity("jalo", "jalo@qq.com"));
		elasticSearchTestRepository.save(new ElasticSearchTestEntity("jalodeng", "jalodeng@qq.com"));
		elasticSearchTestRepository.save(new ElasticSearchTestEntity("yym", "yym@qq.com"));
		
		return findAll(0, 10);
	}
	
	/**
	 * Query all.
	 * 
	 * @param page
	 * @param size
	 * @return List<ElasticSearchTestEntity>
	 */
	public List<ElasticSearchTestEntity> findAll(Integer page, Integer size) {
		List<ElasticSearchTestEntity> list = new ArrayList<>();
		
		elasticSearchTestRepository.findAll(new PageRequest(page, size)).forEach(model -> {
			list.add(model);
		});;
		
		return list;
	}
	
	/**
	 * Find distinct data by name or email
	 * 
	 * @param name
	 * @param email
	 * @param page
	 * @param size
	 * @return List<ElasticSearchTestEntity>
	 */
	public List<ElasticSearchTestEntity> find(String name, String email, Integer page, Integer size) {
		List<ElasticSearchTestEntity> list = new ArrayList<>();
		
		elasticSearchTestRepository.findDistinctElasticSearchTestEntityByNameContainingOrEmailContaining(name, email, new PageRequest(page, size)).forEach(model -> {
			list.add(model);
		});;
		
		return list;
	}
}
