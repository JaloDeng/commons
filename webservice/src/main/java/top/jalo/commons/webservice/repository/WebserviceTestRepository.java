package top.jalo.commons.webservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import top.jalo.commons.webservice.entity.WebserviceTestEntity;

/**
 * Repository : Test
 * 
 * @Author JALO
 * 
 */
public interface WebserviceTestRepository extends JpaRepository<WebserviceTestEntity, Long>, JpaSpecificationExecutor<WebserviceTestEntity> {

}
