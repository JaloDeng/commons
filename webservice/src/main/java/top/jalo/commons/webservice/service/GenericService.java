package top.jalo.commons.webservice.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import top.jalo.commons.webservice.model.Sorter;

/**
 * Generic Service
 *
 * @Author JALO
 *
 * @param <E>
 * @param <M>
 * @param <EID>
 * @param <MID>
 */
public abstract class GenericService<E, M, EID extends Serializable, MID extends Serializable> {

	@Autowired
	private JpaRepository<E, EID> jpaRepository;
	
	@Autowired
	private JpaSpecificationExecutor<E> jpaSpecificationExecutor;
	
	protected abstract E createEntity(M model, Object... args) throws Exception;
	protected abstract M createModel(E entity, Object... args) throws Exception;
	
	public E convertToEntity(M model, Object... args) throws Exception {
		if (model == null) {
			return null;
		}
		return createEntity(model, args);
	}
	
	public M convertToModel(E entity, Object... args) throws Exception {
		if (entity == null) {
			return null;
		}
		return createModel(entity, args);
	}
	
	@SuppressWarnings("unchecked")
	protected EID convertToEntityId(MID modelId) {
		return (EID) modelId;
	}
	
	@SuppressWarnings("unchecked")
	protected MID convertToModelId(EID entityId) {
		return (MID) entityId;
	}
	
	public M findById(MID id, Object... args) throws Exception {
		if (id == null) {
			return null;
		}
		E entity = jpaRepository.findOne(convertToEntityId(id));
		return convertToModel(entity, args);
	}
	
	public Collection<M> findByIds(Collection<MID> ids, Object... args) {
		List<E> entities = jpaRepository.findAll(ids.stream().map(this::convertToEntityId).collect(Collectors.toList()));
		if (entities == null || entities.isEmpty()) {
			return null;
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
	 * Unfinished.
	 *
	 * @param query
	 * @param page
	 * @param size
	 * @param sorters
	 * @param args
	 * @return
	 */
	public Page<M> find(Collection<String> query, Integer page, Integer size, List<Sorter> sorters, Object... args) {
		ServiceSupport.createPageRequest(page, size, sorters);
		jpaSpecificationExecutor.count(null);
		return null;
	}
	
	public M save(M model, Object... args) throws Exception {
		E entity = convertToEntity(model, args);
		E resultEntity = jpaRepository.saveAndFlush(entity);
		return convertToModel(resultEntity);
	}
	
	public M deleteById(MID id, Object... args) {
		E entity = jpaRepository.findOne(convertToEntityId(id));
		if (entity == null) {
			// TODO: handle exception
		}
		return delete(entity, args);
	}
	
	protected M delete(E entity, Object... args) {
		try {
			M model = convertToModel(entity, args);
			jpaRepository.delete(entity);
			return model;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
}
