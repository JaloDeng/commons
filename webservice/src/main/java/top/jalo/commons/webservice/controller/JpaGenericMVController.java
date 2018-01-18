package top.jalo.commons.webservice.controller;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
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
public abstract class JpaGenericMVController<E, M, EID extends Serializable, MID extends Serializable> extends JpaGenericController<E, M, Serializable, Serializable> {

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
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView findByIdAndView(@PathVariable MID id, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
//		return service.findById(id, model, "");
		return null;
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
		return service.findAll(page, size, sorts, String.format("%sGrid", ViewFinder.getView(this.getClass())));
	}

	/**
	 * Create by m.
	 *
	 * @param m
	 * @param model
	 * @param request
	 * @param response
	 * @return ModelAndView
	 * @throws Exception
	 */
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ModelAndView create(@RequestBody M m, Model model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return service.create(m, model, "");
	}

	/**
	 * Update all column by model and id.
	 *
	 * @param id
	 * @param m
	 * @param model
	 * @param request
	 * @param response
	 * @return ModelAndView
	 * @throws Exception
	 */
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView fullUpdateById(@PathVariable MID id, @RequestBody M m, Model model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return service.fullUpdateById(id, m, model, "");
	}

	/**
	 * Update all column by model and id.
	 *
	 * @param id
	 * @param m
	 * @param model
	 * @param request
	 * @param response
	 * @return ModelAndView
	 * @throws Exception
	 */
	@PatchMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView partialUpdateById(@PathVariable MID id, @RequestBody M m, Model model,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		return service.partialUpdateById(id, m, model, "");
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
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView deleteById(@PathVariable MID id, Model model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return service.deleteById(id, model, "");
	}
}
