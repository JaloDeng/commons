package top.jalo.commons.search.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import top.jalo.commons.search.entity.ElasticSearchTestEntity;

/**
 * Repository : Test ElasticSearch
 * 
 * @Author JALO
 *
 */
public interface ElasticSearchTestRepository extends ElasticsearchRepository<ElasticSearchTestEntity, String> {

	/**
	 * Query distinct data by name or email
	 * 
	 * @param name
	 * @param email
	 * @param pageable
	 * @return Page<ElasticSearchTestEntity>
	 */
	Page<ElasticSearchTestEntity> findDistinctElasticSearchTestEntityByNameContainingOrEmailContaining(String name, String email, Pageable pageable);
}
