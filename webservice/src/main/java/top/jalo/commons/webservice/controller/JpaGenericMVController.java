package top.jalo.commons.webservice.controller;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import top.jalo.commons.util.annotation.ViewFinder;

/**
 * Generic Controller for JPA which all methods return ModelAndView.
 *
 * @Author JALO
 *
 */
public abstract class JpaGenericMVController<E, M, EID extends Serializable, MID extends Serializable>
		extends JpaGenericController<E, M, EID, MID> {

	/**
	 * Get the empty form for create.
	 * 
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	@GetMapping("/form")
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView createForm(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView(String.format("%sForm", ViewFinder.getView(this)));
	}
	
	/**
	 * Query one by id.
	 *
	 * @param id
	 * @param model
	 * @param request
	 * @param response
	 * @return ModelAndView
	 * @throws Exception
	 */
	@GetMapping("/form/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView findByIdAndView(@PathVariable MID id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return service.findByIdAndView(id, String.format("%sForm", ViewFinder.getView(this)));
	}

	/**
	 * Query all and return grid.
	 *
	 * @param page
	 * @param size
	 * @param sorts
	 * @param request
	 * @param response
	 * @return ModelAndView
	 * @throws Exception
	 */
	@GetMapping("/grid")
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView findAllAndView(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer size, @RequestParam(required = false) String sorts,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		return service.findAllAndView(page, size, sorts, String.format("%sGrid", ViewFinder.getView(this)));
	}

	/**
	 * Create by model.
	 *
	 * @param model
	 * @param request
	 * @param response
	 * @return ModelAndView
	 * @throws Exception
	 */
	@PostMapping("/form")
	@ResponseStatus(HttpStatus.CREATED)
	public ModelAndView createAndView(@RequestBody M model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return service.createAndView(model, String.format("%sForm", ViewFinder.getView(this)));
	}

	/**
	 * Update all column by model and id.
	 *
	 * @param id
	 * @param model
	 * @param request
	 * @param response
	 * @return ModelAndView
	 * @throws Exception
	 */
	@PutMapping("/form/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView fullUpdateByIdAndView(@PathVariable MID id, @RequestBody M model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return service.fullUpdateByIdAndView(id, model, String.format("%sForm", ViewFinder.getView(this)));
	}

	/**
	 * Update all column by model and id.
	 *
	 * @param id
	 * @param model
	 * @param request
	 * @param response
	 * @return ModelAndView
	 * @throws Exception
	 */
	@PatchMapping("/form/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView partialUpdateByIdAndView(@PathVariable MID id, @RequestBody M model,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		return service.partialUpdateByIdAndView(id, model, String.format("%sForm", ViewFinder.getView(this)));
	}

	/**
	 * Delete by id.
	 *
	 * @param id
	 * @param model
	 * @param request
	 * @param response
	 * @return ModelAndView
	 * @throws Exception
	 */
	@DeleteMapping("/form/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView deleteByIdAndView(@PathVariable MID id, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return service.deleteByIdAndView(id, String.format("%sGrid", ViewFinder.getView(this)));
	}
}
