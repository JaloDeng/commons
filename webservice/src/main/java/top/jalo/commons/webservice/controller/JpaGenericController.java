package top.jalo.commons.webservice.controller;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import top.jalo.commons.webservice.service.JpaGenericService;

/**
 * Generic Controller for JPA.
 *
 * @Author JALO
 *
 * @param <M>
 * @param <MID>
 */
public abstract class JpaGenericController<M, MID extends Serializable> {

	@Autowired
	private JpaGenericService<?, M, ?, MID> service;

	protected abstract JpaGenericService<?, M, ?, MID> getService();

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody M findById(@PathVariable MID id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return service.findById(id);
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<M> findAll(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer size, @RequestParam(required = false) String sorts,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		return service.findAll(page, size, sorts);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody M create(@RequestBody M model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return service.create(model);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody M fullUpdateById(@PathVariable MID id, @RequestBody M model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return service.fullUpdateById(id, model);
	}
	
	@PatchMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody M partialUpdateById(@PathVariable MID id, @RequestBody M model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return service.partialUpdateById(id, model);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody M deleteById(@PathVariable MID id) throws Exception {
		return service.deleteById(id);
	}
}
