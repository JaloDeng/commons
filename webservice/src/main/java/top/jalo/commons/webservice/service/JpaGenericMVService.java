package top.jalo.commons.webservice.service;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.web.servlet.ModelAndView;

import top.jalo.commons.util.StringUtils;

/**
 * Generic Service for JPA that all methods return ModelAndView.
 *
 * @Author JALO
 *
 * @param <E>
 * @param <M>
 * @param <EID>
 * @param <MID>
 */
public abstract class JpaGenericMVService<E, M, EID extends Serializable, MID extends Serializable> extends JpaGenericService<E, M, EID, MID> {

	/**
	 * Query one by id.
	 *
	 * @param modelId
	 * @param viewName
	 * @param args
	 * @return ModelAndView
	 * @throws Exception
	 */
	public ModelAndView findById(String viewName, MID modelId, Object... args) throws Exception {
		StringUtils.isViewNameBlank(viewName);
		return new ModelAndView(viewName, "result", findById(modelId));
	}

	/**
	 * Query collection by id's collection.
	 *
	 * @param viewName
	 * @param modelIds
	 * @param model
	 * @param args
	 * @return ModelAndView
	 * @throws Exception
	 */
	public ModelAndView findByIds(String viewName, Collection<MID> modelIds, Object... args)
			throws Exception {
		StringUtils.isViewNameBlank(viewName);
		return new ModelAndView(viewName, "result", findByIds(modelIds));
	}

	/**
	 * Query all.
	 *
	 * @param viewName
	 * @param page
	 * @param size
	 * @param sorts
	 * @param args
	 * @return ModelAndView
	 * @throws Exception
	 */
	public ModelAndView findAll(String viewName, Integer page, Integer size, String sorts, Object... args)
			throws Exception {
		StringUtils.isViewNameBlank(viewName);
		return new ModelAndView(viewName, "result", findAll(page, size, sorts, args));
	}

	/**
	 * Create by model.
	 *
	 * @param viewName
	 * @param model
	 * @param args
	 * @return ModelAndView
	 * @throws Exception
	 */
	public ModelAndView create(String viewName, M model, Object... args) throws Exception {
		StringUtils.isViewNameBlank(viewName);
		return new ModelAndView(viewName, "result", create(model, args));
	}

	/**
	 * Update all column by model and id.
	 *
	 * @param viewName
	 * @param modelId
	 * @param model
	 * @param args
	 * @return ModelAndView
	 * @throws Exception
	 */
	public ModelAndView fullUpdateById(String viewName, MID modelId, M model, Object... args)
			throws Exception {
		StringUtils.isViewNameBlank(viewName);
		return new ModelAndView(viewName, "result", fullUpdateById(modelId, model, args));
	}

	/**
	 * Update partial column by model and id.
	 *
	 * @param viewName
	 * @param modelId
	 * @param model
	 * @param args
	 * @return ModelAndView
	 * @throws Exception
	 */
	public ModelAndView partialUpdateById(String viewName, MID modelId, M model, Object... args)
			throws Exception {
		StringUtils.isViewNameBlank(viewName);
		return new ModelAndView(viewName, "result", partialUpdateById(modelId, model, args));
	}

	/**
	 * Delete by id.
	 *
	 * @param viewName
	 * @param modelId
	 * @param args
	 * @return ModelAndView
	 * @throws Exception
	 */
	public ModelAndView deleteById(String viewName, MID modelId, Object... args) throws Exception {
		StringUtils.isViewNameBlank(viewName);
		return new ModelAndView(viewName, "result", deleteById(modelId, args));
	}
}
