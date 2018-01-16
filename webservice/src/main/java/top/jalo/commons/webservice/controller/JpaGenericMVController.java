package top.jalo.commons.webservice.controller;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
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

import top.jalo.commons.webservice.service.JpaGenericService;

/**
 * Generic Controller for JPA which all methods return ModelAndView.
 *
 * @Author JALO
 *
 */
public abstract class JpaGenericMVController<M, MID extends Serializable> {

	@Autowired
	private JpaGenericService<?, M, ?, MID> service;

	protected abstract JpaGenericService<?, M, ?, MID> getService();

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView findById(@PathVariable MID id, Model model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return service.findById(id, model, "");
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView findAll(Model model, @RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer size, @RequestParam(required = false) String sorts,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		return service.findAll(page, size, sorts, model, "");
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ModelAndView create(@RequestBody M m, Model model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return service.create(m, model, "");
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView fullUpdateById(@PathVariable MID id, @RequestBody M m, Model model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return service.fullUpdateById(id, m, model, "");
	}

	@PatchMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView partialUpdateById(@PathVariable MID id, @RequestBody M m, Model model,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		return service.partialUpdateById(id, m, model, "");
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView deleteById(@PathVariable MID id, Model model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return service.deleteById(id, model, "");
	}
}
