package top.jalo.commons.webservice.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import top.jalo.commons.webservice.exception.HttpStatusException;
import top.jalo.commons.webservice.model.CollectionResult;
import top.jalo.commons.webservice.model.Result;
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

	private static final Logger LOGGER = LoggerFactory.getLogger(JpaGenericService.class);

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
	 * @return Result<M>
	 * @throws Exception
	 */
	public Result<M> findById(MID modelId, Object... args) throws Exception {
		if (modelId == null) {
			LOGGER.error("Id is null.");
			return new Result<>(new Exception("Id is null."));
		}

		E entity = jpaRepository.findOne(convertToEntityId(modelId));
		if (entity == null) {
			LOGGER.error("Can not find model where id is [{}].", modelId);
			//return new Result<>(new Exception(String.format("Can not find model where id is [%s].", modelId.toString())));
			throw new HttpStatusException(String.format("Can not find model where id is %s.", modelId.toString()));
		}
		M model = convertToModel(entity, args);
		LOGGER.info("Model : " + model.toString());
		return new Result<M>(model);
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
		List<E> entities = jpaRepository
				.findAll(modelIds.stream().map(this::convertToEntityId).collect(Collectors.toList()));
		if (entities == null || entities.isEmpty()) {
			LOGGER.error("Can not find models by ids.");
			// TODO: handle exception and throws result message.
			throw new Exception("Can not find models by ids.");
		}

		return entities.stream().map(entity -> {
			try {
				return convertToModel(entity, args);
			} catch (Exception e) {
				LOGGER.error(e.toString());
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
	 * @return CollectionResult<M>
	 * @throws Exception
	 */
	public CollectionResult<M> findAll(Integer page, Integer size, String sorts, Object... args) throws Exception {
		Pageable pageable = ServiceSupport.createPageRequest(page - 1, size, Sorter.parse(sorts));
		Page<E> entities = jpaRepository.findAll(pageable);
		Page<M> models = new PageImpl<>(entities.getContent().stream().map(entity -> {
			try {
				return convertToModel(entity, args);
			} catch (Exception e) {
				return null;
			}
		}).collect(Collectors.toList()), pageable, entities.getTotalElements());
		if (models.getTotalElements() == 0) {
			LOGGER.info("Data is empty.");
		} else {
			LOGGER.info("Data's total is [{}], total page is [{}], current count is [{}], current page is [{}].", models.getTotalElements(),
					models.getTotalPages(), models.getNumberOfElements(), models.getTotalElements() == 0 ? 0 :models.getNumber() + 1);
			LOGGER.info("Data's collection : " + models.getContent().toString());
		}
		return new CollectionResult<M>(models);
	}

	/**
	 * Unfinished.
	 *
	 * @param query
	 * @param page
	 * @param size
	 * @param sorters
	 * @param args
	 * @return Result<M>
	 * @throws Exception
	 */
	public Page<M> find(Collection<String> query, Integer page, Integer size, String sorts, Object... args)
			throws Exception {
		ServiceSupport.createPageRequest(page - 1, size, Sorter.parse(sorts));
		jpaSpecificationExecutor.count(null);
		return null;
	}

	/**
	 * Create by model.
	 * 
	 * @param model
	 * @param args
	 * @return Result<M>
	 * @throws Exception
	 */
	public Result<M> create(M model, Object... args) throws Exception {
		try {
			E entity = convertToEntity(model, null, false, args);
			jpaRepository.saveAndFlush(entity);
			M resultModel = convertToModel(entity, args);
			LOGGER.info("Success to create entity : " + resultModel.toString());
			return new Result<M>(resultModel);
		} catch (Exception e) {
			LOGGER.error("Fail to create entity : " + e.toString());
			return new Result<>(new Exception("Fail to create entity : " + e.getMessage()));
		}
	}

	/**
	 * Update all column by model and id.
	 * 
	 * @param modelId
	 * @param model
	 * @param args
	 * @return Result<M>
	 * @throws Exception
	 */
	public Result<M> fullUpdateById(MID modelId, M model, Object... args) throws Exception {
		E referenceEntity = jpaRepository.findOne(convertToEntityId(modelId));
		if (referenceEntity == null) {
			LOGGER.error("Can not find entity where id is [{}] to update full.", modelId);
			return new Result<>(new Exception(String.format("Can not find entity where id is [%s] to update full.", modelId.toString())));
		}
		return fullUpdate(referenceEntity, model, args);
	}

	/**
	 * Update all column by model.
	 * 
	 * @param referenceEntity
	 * @param model
	 * @param args
	 * @return Result<M>
	 * @throws Exception
	 */
	protected Result<M> fullUpdate(E referenceEntity, M model, Object... args) throws Exception {
		try {
			E entity = convertToEntity(model, referenceEntity, false, args);
			jpaRepository.saveAndFlush(entity);
			M resultModel = convertToModel(entity, args);
			LOGGER.info("Success to update entity full : " + resultModel.toString());
			return new Result<M>(resultModel);
		} catch (Exception e) {
			LOGGER.error(e.toString());
			return new Result<>(new Exception("Fail to update entity full : " + e.getMessage()));
		}
	}

	/**
	 * Update partial column by model and id.
	 * 
	 * @param modelId
	 * @param model
	 * @param args
	 * @return Result<M>
	 * @throws Exception
	 */
	public Result<M> partialUpdateById(MID modelId, M model, Object... args) throws Exception {
		E referenceEntity = jpaRepository.findOne(convertToEntityId(modelId));
		if (referenceEntity == null) {
			LOGGER.error("Can not find entity where id is [{}] to update partial.", modelId);
			return new Result<>(new Exception(String.format("Can not find entity where id is [%s] to update partial.", modelId.toString())));
		}
		return partialUpdate(referenceEntity, model, args);
	}

	/**
	 * Update partial column by model.
	 * 
	 * @param referenceEntity
	 * @param model
	 * @param args
	 * @return Result<M>
	 * @throws Exception
	 */
	protected Result<M> partialUpdate(E referenceEntity, M model, Object... args) throws Exception {
		try {
			E entity = convertToEntity(model, referenceEntity, true, args);
			jpaRepository.saveAndFlush(entity);
			M resultModel = convertToModel(entity, args);
			LOGGER.info("Success to update entity partial : " + resultModel.toString());
			return new Result<M>(resultModel);
		} catch (Exception e) {
			LOGGER.error(e.toString());
			return new Result<>(new Exception("Fail to update entity partial : " + e.getMessage()));
		}
	}

	/**
	 * Delete by id.
	 * 
	 * @param modelId
	 * @param args
	 * @return Result<M>
	 * @throws Exception
	 */
	public Result<M> deleteById(MID modelId, Object... args) throws Exception {
		E entity = jpaRepository.findOne(convertToEntityId(modelId));
		if (entity == null) {
			LOGGER.error("Can not find entity where id is [{}] to delete.", modelId);
			return new Result<>(new Exception(String.format("Can not find entity where id is [%s] to delete.", modelId.toString())));
		}
		return delete(entity, args);
	}

	/**
	 * Delete by entity.
	 * 
	 * @param entity
	 * @param args
	 * @return Result<M>
	 * @throws Exception
	 */
	protected Result<M> delete(E entity, Object... args) throws Exception {
		try {
			M model = convertToModel(entity, args);
			jpaRepository.delete(entity);
			LOGGER.info("Success to delete entity : " + model.toString());
			return new Result<M>(model);
		} catch (Exception e) {
			LOGGER.error(e.toString());
			return new Result<>(new Exception("Fail to delete entity : " + e.getMessage()));
		}
	}
}
