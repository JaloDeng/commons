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
public class UserService extends JpaGenericService<WebserviceTestEntity, WebserviceTest, Long, Long> {

	@Override
	protected WebserviceTestEntity createEntity(WebserviceTest model, WebserviceTestEntity referenceEntity,
			Boolean mergeWhenNotNull, Object... args) throws Exception {
		if (model == null) {
			return null;
		}

		WebserviceTestEntity entity = referenceEntity == null ? new WebserviceTestEntity() : referenceEntity;
		if (!mergeWhenNotNull && model.getName() != null) {
			entity.setName(model.getName());
		}
		if (!mergeWhenNotNull && model.getAge() != null) {
			entity.setAge(model.getAge());
		}
		if (!mergeWhenNotNull && model.getEmail() != null) {
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
