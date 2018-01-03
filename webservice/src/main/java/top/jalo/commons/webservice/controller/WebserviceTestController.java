package top.jalo.commons.webservice.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import top.jalo.commons.webservice.model.WebserviceTest;
import top.jalo.commons.webservice.service.WebserviceTestService;

/**
 * Controller : Test
 * 
 * @Author JALO
 *
 */
@RestController
@RequestMapping("/test")
public class WebserviceTestController {

	@Autowired
	private WebserviceTestService webserviceTestService;

	/**
	 * Query All. <br>
	 * 
	 * URL : <br>
	 * (1) ./test (default: page = 1, size = 10) <br>
	 * (2) ./test?page=1&size=2&sorts=[{"property": "age", "direction": "ASC"}, {"property": "id", "direction": "DESC"}] <br>
	 * METHOR : GET <br>
	 * 
	 * @param page
	 * @param size
	 * @param orders
	 * @param request
	 * @param response
	 * @return list
	 */
	@GetMapping
	public @ResponseBody List<WebserviceTest> findAll(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer size, @RequestParam(required = false) String sorts,
			HttpServletRequest request, HttpServletResponse response) {
		return webserviceTestService.findAll(page, size, sorts);
	}

	/**
	 * Query by id. <br>
	 * 
	 * URL : ./test/1 <br>
	 * METHOR : GET <br>
	 * 
	 * @param id
	 * @param request
	 * @param response
	 * @return model
	 */
	@GetMapping("/{id}")
	public @ResponseBody WebserviceTest findOne(@PathVariable Long id, HttpServletRequest request,
			HttpServletResponse response) {
		return webserviceTestService.findOne(id);
	}

	/**
	 * Save if model has not id. Update if model has id. <br>
	 * 
	 * URL : ./test <br>
	 * METHOR : POST <br>
	 * CONTENT-TYPE : application/json <br>
	 * BODY : <br>
	 * (1) save: {"name": "JALO", "age": 18, "email": "jalo@qq.com"} <br>
	 * (2) update: {"id": 1, "name": "JALO", "age": 18, "email": "jalo@qq.com"} <br>
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return model
	 */
	@PostMapping
	public @ResponseBody WebserviceTest saveOrUpdate(@RequestBody WebserviceTest model, HttpServletRequest request,
			HttpServletResponse response) {
		return webserviceTestService.saveOrUpdate(model);
	}

	/**
	 * Delete by id. <br>
	 * 
	 * URL : ./test/1 <br>
	 * METHOR : DELETE <br>
	 * 
	 * @param id
	 * @param request
	 * @param response
	 * @return id
	 */
	@DeleteMapping("/{id}")
	public @ResponseBody Long delete(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
		return webserviceTestService.delete(id);
	}
}
