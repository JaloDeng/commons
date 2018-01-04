package top.jalo.commons.webservice.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import top.jalo.commons.webservice.model.Sorter;

/**
 * Generic Service for JPA.
 *
 * @Author JALO
 *
 * @param <E>
 * @param <M>
 * @param <EID>
 * @param <MID>
 */
public abstract class JpaGenericService<E, M, EID extends Serializable, MID extends Serializable> {

	@Autowired
	private JpaRepository<E, EID> jpaRepository;
	
	@Autowired
	private JpaSpecificationExecutor<E> jpaSpecificationExecutor;
	
	protected abstract E createEntity(M model, E referenceEntity, Boolean mergeIfNotNull, Object... args) throws Exception;
	protected abstract M createModel(E entity, Object... args) throws Exception;
	
	/**
	 * Convert model to entity.
	 * 
	 * @param model
	 * @param args
	 * @return entity
	 * @throws Exception
	 */
	public E convertToEntity(M model, E referenceEntity, Boolean mergeIfNotNull, Object... args) throws Exception {
		if (model == null) {
			return null;
		}
		return createEntity(model, referenceEntity, false, args);
	}
	
	/**
	 * Convert entity to model.
	 * 
	 * @param entity
	 * @param args
	 * @return model
	 * @throws Exception
	 */
	public M convertToModel(E entity, Object... args) throws Exception {
		if (entity == null) {
			return null;
		}
		return createModel(entity, args);
	}
	
	/**
	 * Convert id(model) to id(entity).
	 * 
	 * @param modelId
	 * @return entityId
	 */
	@SuppressWarnings("unchecked")
	protected EID convertToEntityId(MID modelId) {
		return (EID) modelId;
	}
	
	/**
	 * Convert id(entity) to id(model).
	 * 
	 * @param entityId
	 * @return modelId
	 */
	@SuppressWarnings("unchecked")
	protected MID convertToModelId(EID entityId) {
		return (MID) entityId;
	}
	
	/**
	 * Query one by id.
	 * 
	 * @param modelId
	 * @param args
	 * @return model
	 * @throws Exception
	 */
	public M findById(MID modelId, Object... args) throws Exception {
		if (modelId == null) {
			// TODO: handle exception and throws result message.
		}
		
		E entity = jpaRepository.findOne(convertToEntityId(modelId));
		if(entity == null) {
			// TODO: handle exception and throws result message.
		}
		
		return convertToModel(entity, args);
	}
	
	/**
	 * Query collection by id's collection.
	 * 
	 * @param modelIds
	 * @param args
	 * @return modelCollection
	 * @throws Exception
	 */
	public Collection<M> findByIds(Collection<MID> modelIds, Object... args) throws Exception {
		List<E> entities = jpaRepository.findAll(modelIds.stream().map(this::convertToEntityId).collect(Collectors.toList()));
		if (entities == null || entities.isEmpty()) {
			// TODO: handle exception and throws result message.
		}
		
		return entities.stream().map(entity -> {
			try {
				return convertToModel(entity, args);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}).collect(Collectors.toList());
	}
	
	/**
	 * Query all.
	 * 
	 * @param page
	 * @param size
	 * @param sorts
	 * @param args
	 * @return modelList
	 * @throws Exception
	 */
	public List<M> findAll(Integer page, Integer size, String sorts, Object... args) throws Exception {
		Pageable pageable = ServiceSupport.createPageRequest(page - 1, size, Sorter.parse(sorts));
		Page<E> entityCollection = jpaRepository.findAll(pageable);
		List<M> modelList = new ArrayList<>();
		entityCollection.forEach(entity -> {
			try {
				modelList.add(convertToModel(entity));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		return modelList;
	}
	
	/**
	 * Unfinished.
	 *
	 * @param query
	 * @param page
	 * @param size
	 * @param sorters
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public Page<M> find(Collection<String> query, Integer page, Integer size, String sorts, Object... args) throws Exception {
		ServiceSupport.createPageRequest(page - 1, size, Sorter.parse(sorts));
		jpaSpecificationExecutor.count(null);
		return null;
	}
	
	/**
	 * Create by model.
	 * 
	 * @param model
	 * @param args
	 * @return model
	 * @throws Exception
	 */
	public M create(M model, Object... args) throws Exception {
		try {
			E entity = convertToEntity(model, null, false, args);
			jpaRepository.saveAndFlush(entity);
			return convertToModel(entity);
		} catch (Exception e) {
			// TODO: handle exception and throws result message.
		}
		return null;
	}
	
	/**
	 * Update all column by model and id.
	 * 
	 * @param modelId
	 * @param model
	 * @param args
	 * @return model
	 * @throws Exception
	 */
	public M fullUpdateById(MID modelId, M model, Object... args) throws Exception {
		E referenceEntity = jpaRepository.findOne(convertToEntityId(modelId));
		if (referenceEntity == null) {
			// TODO: handle exception and throws result message.
		}
		return fullUpdate(referenceEntity, model, args);
	}
	
	/**
	 * Update all column by model.
	 * 
	 * @param referenceEntity
	 * @param model
	 * @param args
	 * @return model
	 * @throws Exception
	 */
	protected M fullUpdate(E referenceEntity, M model, Object... args) throws Exception {
		try {
			E entity = convertToEntity(model, referenceEntity, false, args);
			jpaRepository.saveAndFlush(entity);
			return convertToModel(entity, args);
		} catch (Exception e) {
			// TODO: handle exception and throws result message.
		}
		return null;
	}
	
	/**
	 * Update partial column by model and id.
	 * 
	 * @param modelId
	 * @param model
	 * @param args
	 * @return model
	 * @throws Exception
	 */
	public M partialUpdateById(MID modelId, M model, Object... args) throws Exception {
		E referenceEntity = jpaRepository.findOne(convertToEntityId(modelId));
		if (referenceEntity == null) {
			// TODO: handle exception and throws result message.
		}
		return partialUpdate(referenceEntity, model, args);
	}
	
	/**
	 * Update partial column by model.
	 * 
	 * @param referenceEntity
	 * @param model
	 * @param args
	 * @return model
	 * @throws Exception
	 */
	protected M partialUpdate(E referenceEntity, M model, Object... args) throws Exception {
		try {
			E entity = convertToEntity(model, referenceEntity, true, args);
			jpaRepository.saveAndFlush(entity);
			return convertToModel(entity, args);
		} catch (Exception e) {
			// TODO: handle exception and throws result message.
		}
		return null;
	}
	
	/**
	 * Delete by id.
	 * 
	 * @param modelId
	 * @param args
	 * @return model
	 * @throws Exception
	 */
	public M deleteById(MID modelId, Object... args) throws Exception {
		E entity = jpaRepository.findOne(convertToEntityId(modelId));
		if (entity == null) {
			// TODO: handle exception and throws result message.
		}
		return delete(entity, args);
	}
	
	/**
	 * Delete by entity.
	 * 
	 * @param entity
	 * @param args
	 * @return model
	 * @throws Exception
	 */
	protected M delete(E entity, Object... args) throws Exception {
		try {
			M model = convertToModel(entity, args);
			jpaRepository.delete(entity);
			return model;
		} catch (Exception e) {
			// TODO: handle exception and throws result message.
		}
		return null;
	}
}
