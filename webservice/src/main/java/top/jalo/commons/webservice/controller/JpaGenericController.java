package top.jalo.commons.webservice.controller;

import java.io.Serializable;
import java.util.Map;

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

	/**
	 * Query one by id. <br>
	 * 
	 * URL : ./user/1 <br>
	 * METHOR : GET <br>
	 * 
	 * @param id
	 * @param request
	 * @param response
	 * @return model
	 * @throws Exception
	 */
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody M findById(@PathVariable MID id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return service.findById(id);
	}

	/**
	 * Query all. <br>
	 * 
	 * URL : <br>
	 * (1) ./user (default: page = 1, size = 10) <br>
	 * (2) ./user?page=1&size=2&sorts=[{"property": "age", "direction": "ASC"}, {"property": "id", "direction": "DESC"}] <br>
	 * METHOR : GET <br>
	 * 
	 * @param page
	 * @param size
	 * @param sorts
	 * @param request
	 * @param response
	 * @return modelList
	 * @throws Exception
	 */
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Map<String, Object> findAll(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer size, @RequestParam(required = false) String sorts,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		return service.findAll(page, size, sorts);
	}

	/**
	 * Create by model.
	 * 
	 * URL : ./user
	 * METHOR : POST <br>
	 * CONTENT-TYPE : application/json <br>
	 * BODY : {"name": "JALO", "age": 18, "email": "jalo@qq.com"} <br>
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return model
	 * @throws Exception
	 */
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody M create(@RequestBody M model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return service.create(model);
	}

	/**
	 * Update all column by model and id.
	 * 
	 * URL : ./user
	 * METHOR : PUT <br>
	 * CONTENT-TYPE : application/json <br>
	 * BODY : {"id": 1, "name": "JALO", "age": 18, "email": "jalo@qq.com"} <br>
	 * 
	 * @param id
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody M fullUpdateById(@PathVariable MID id, @RequestBody M model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return service.fullUpdateById(id, model);
	}
	
	/**
	 * Update partial column by model and id.
	 * 
	 * URL : ./user
	 * METHOR : PATCH <br>
	 * CONTENT-TYPE : application/json <br>
	 * BODY : {"id": 1, "name": "JALO", "age": 18, "email": "jalo@qq.com"} <br>
	 * 
	 * @param id
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@PatchMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody M partialUpdateById(@PathVariable MID id, @RequestBody M model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return service.partialUpdateById(id, model);
	}
	
	/**
	 * Delete by id.
	 * 
	 * URL : ./user/1 <br>
	 * METHOR : DELETE <br>
	 * 
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody M deleteById(@PathVariable MID id, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return service.deleteById(id);
	}
}
