package top.jalo.commons.webservice.service;

import org.springframework.stereotype.Service;

import top.jalo.commons.webservice.entity.WebserviceTestEntity;
import top.jalo.commons.webservice.model.WebserviceTest;

/**
 * Service : Test UserService which extend GenericService<E, M, EID extends Serializable, MID extends Serializable>.
 *
 * @Author JALO
 *
 */
@Service
public class UserService extends GenericService<WebserviceTestEntity, WebserviceTest, Long, Long> {

	@Override
	protected WebserviceTestEntity createEntity(WebserviceTest model, Object... args) throws Exception {
		if (model == null) {
			return null;
		}
		
		WebserviceTestEntity entity = new WebserviceTestEntity();
		if (model.getId() != null) {
			entity.setId(model.getId());
		}
		if (model.getName() != null && !"".equals(model.getName())) {
			entity.setName(model.getName());
		}
		if (model.getAge() != null) {
			entity.setAge(model.getAge());
		}
		if (model.getEmail() != null && !"".equals(model.getEmail())) {
			entity.setEmail(model.getEmail());
		}
		
		return entity;
	}

	@Override
	protected WebserviceTest createModel(WebserviceTestEntity entity, Object... args) throws Exception {
		if (entity == null) {
			return null;
		}
		
		WebserviceTest model = new WebserviceTest();
		
		model.setId(entity.getId());
		model.setName(entity.getName());
		model.setAge(entity.getAge());
		model.setEmail(entity.getEmail());
		
		return model;
	}

}
