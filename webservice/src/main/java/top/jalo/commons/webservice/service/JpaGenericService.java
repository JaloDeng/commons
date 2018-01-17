package top.jalo.commons.webservice.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import top.jalo.commons.util.check.StringUtils;
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

	protected abstract E createEntity(M model, E referenceEntity, Boolean mergeIfNotNull, Object... args)
			throws Exception;

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
	 * @param model
	 * @param viewName
	 * @param args
	 * @return ModelAndView
	 * @throws Exception
	 */
	public ModelAndView findById(MID modelId, Model model, String viewName, Object... args) throws Exception {
		StringUtils.isViewNameBlank(viewName);
		model.addAttribute("data", findById(modelId));
		model.addAttribute("success", true);
		return new ModelAndView(viewName, "result", model);
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
			LOGGER.error("Id is null.");
			// TODO: handle exception and throws result message.
			throw new Exception("Id is null.");
		}

		E entity = jpaRepository.findOne(convertToEntityId(modelId));
		if (entity == null) {
			LOGGER.error("Can not find model where id is [{}].", modelId);
			// TODO: handle exception and throws result message.
			throw new Exception(String.format("Can not find model where id is [%s].", modelId.toString()));
		}
		M model = convertToModel(entity, args);
		LOGGER.info("Model : " + model.toString());
		return model;
	}

	/**
	 * Query collection by id's collection.
	 *
	 * @param modelIds
	 * @param model
	 * @param viewName
	 * @param args
	 * @return ModelAndView
	 * @throws Exception
	 */
	public ModelAndView findByIds(Collection<MID> modelIds, Model model, String viewName, Object... args)
			throws Exception {
		StringUtils.isViewNameBlank(viewName);
		model.addAttribute("data", findByIds(modelIds));
		model.addAttribute("success", true);
		return new ModelAndView(viewName, "result", model);
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
	 * @param model
	 * @param viewName
	 * @param args
	 * @return ModelAndView
	 * @throws Exception
	 */
	public ModelAndView findAll(Integer page, Integer size, String sorts, Model model, String viewName, Object... args)
			throws Exception {
		StringUtils.isViewNameBlank(viewName);
		model.addAttribute("data", findAll(page, size, sorts, args));
		model.addAttribute("success", true);
		return new ModelAndView(viewName, "result", model);
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
	public Map<String, Object> findAll(Integer page, Integer size, String sorts, Object... args) throws Exception {
		Pageable pageable = ServiceSupport.createPageRequest(page - 1, size, Sorter.parse(sorts));
		Page<E> entityCollection = jpaRepository.findAll(pageable);
		List<M> modelList = new ArrayList<>();
		Integer currentPage = entityCollection.getNumber() + 1;
		Integer currentCount = entityCollection.getNumberOfElements();
		Long total = entityCollection.getTotalElements();
		Integer totalPage = entityCollection.getTotalPages();
		entityCollection.forEach(entity -> {
			try {
				modelList.add(convertToModel(entity, args));
			} catch (Exception e) {
				LOGGER.error(e.toString());
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("data", modelList);
		resultMap.put("currentPage", currentPage);
		resultMap.put("currentCount", currentCount);
		resultMap.put("total", total);
		resultMap.put("totalPage", totalPage);
		LOGGER.info("Data's total is [{}], total page is [{}], current count is [{}], current page is [{}].", total,
				totalPage, currentCount, currentPage);
		LOGGER.info("Data's list : " + modelList.toString());
		return resultMap;
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
	public Page<M> find(Collection<String> query, Integer page, Integer size, String sorts, Object... args)
			throws Exception {
		ServiceSupport.createPageRequest(page - 1, size, Sorter.parse(sorts));
		jpaSpecificationExecutor.count(null);
		return null;
	}

	/**
	 * Create by m.
	 *
	 * @param m
	 * @param model
	 * @param viewName
	 * @param args
	 * @return ModelAndView
	 * @throws Exception
	 */
	public ModelAndView create(M m, Model model, String viewName, Object... args) throws Exception {
		StringUtils.isViewNameBlank(viewName);
		model.addAttribute("data", create(m, args));
		model.addAttribute("success", true);
		return new ModelAndView(viewName, "result", model);
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
			M resultModel = convertToModel(entity, args);
			LOGGER.info("Success to create entity : " + resultModel.toString());
			return resultModel;
		} catch (Exception e) {
			LOGGER.error("Fail to create entity : " + e.toString());
			// TODO: handle exception and throws result message.
			throw new Exception("Fail to create entity : " + e.toString());
		}
	}

	/**
	 * Update all column by model and id.
	 *
	 * @param modelId
	 * @param m
	 * @param model
	 * @param viewName
	 * @param args
	 * @return ModelAndView
	 * @throws Exception
	 */
	public ModelAndView fullUpdateById(MID modelId, M m, Model model, String viewName, Object... args)
			throws Exception {
		StringUtils.isViewNameBlank(viewName);
		model.addAttribute("data", fullUpdateById(modelId, m, args));
		model.addAttribute("success", true);
		return new ModelAndView(viewName, "result", model);
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
			LOGGER.error("Can not find entity where id is [{}] to update full.", modelId);
			// TODO: handle exception and throws result message.
			throw new Exception(
					String.format("Can not find entity where id is [%s] to update full.", modelId.toString()));
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
			M resultModel = convertToModel(entity, args);
			LOGGER.info("Success to update entity full : " + resultModel.toString());
			return resultModel;
		} catch (Exception e) {
			LOGGER.error(e.toString());
			// TODO: handle exception and throws result message.
			throw new Exception("Fail to update entity full : " + e.toString());
		}
	}

	/**
	 * Update partial column by model and id.
	 *
	 * @param modelId
	 * @param m
	 * @param model
	 * @param viewName
	 * @param args
	 * @return ModelAndView
	 * @throws Exception
	 */
	public ModelAndView partialUpdateById(MID modelId, M m, Model model, String viewName, Object... args)
			throws Exception {
		StringUtils.isViewNameBlank(viewName);
		model.addAttribute("data", partialUpdateById(modelId, m, args));
		model.addAttribute("success", true);
		return new ModelAndView(viewName, "result", model);
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
			LOGGER.error("Can not find entity where id is [{}] to update partial.", modelId);
			// TODO: handle exception and throws result message.
			throw new Exception(
					String.format("Can not find entity where id is [%s] to update partial.", modelId.toString()));
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
			M resultModel = convertToModel(entity, args);
			LOGGER.info("Success to update entity partial : " + resultModel.toString());
			return resultModel;
		} catch (Exception e) {
			LOGGER.error(e.toString());
			// TODO: handle exception and throws result message.
			throw new Exception("Fail to update entity partial : " + e.toString());
		}
	}

	/**
	 * Delete by id.
	 *
	 * @param modelId
	 * @param model
	 * @param viewName
	 * @param args
	 * @return ModelAndView
	 * @throws Exception
	 */
	public ModelAndView deleteById(MID modelId, Model model, String viewName, Object... args) throws Exception {
		StringUtils.isViewNameBlank(viewName);
		model.addAttribute("data", deleteById(modelId, args));
		model.addAttribute("success", true);
		return new ModelAndView(viewName, "result", model);
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
			LOGGER.error("Can not find entity where id is [{}] to delete.", modelId);
			// TODO: handle exception and throws result message.
			throw new Exception(String.format("Can not find entity where id is [%s] to delete.", modelId.toString()));
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
			LOGGER.info("Success to delete entity : " + model.toString());
			return model;
		} catch (Exception e) {
			LOGGER.error(e.toString());
			// TODO: handle exception and throws result message.
			throw new Exception("Fail to delete entity : " + e.toString());
		}
	}
}
