package top.jalo.commons.search.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import top.jalo.commons.search.entity.ElasticSearchTestEntity;
import top.jalo.commons.search.service.ElasticSearchTestService;

/**
 * Controller : Test ElasticSearch
 * 
 * @Author JALO
 *
 */
@RestController
@RequestMapping("/test")
public class ElasticSearchTestController {

	@Autowired
	private ElasticSearchTestService elasticSearchTestService;

	@GetMapping("/test")
	public @ResponseBody String test(HttpServletRequest request, HttpServletResponse response) {
		return "test";
	}

	/**
	 * Initialize data
	 * 
	 * @return List<ElasticSearchTestEntity>
	 */
	@GetMapping("/initdata")
	public @ResponseBody List<ElasticSearchTestEntity> initData(HttpServletRequest request,
			HttpServletResponse response) {
		return elasticSearchTestService.initData();
	}

	/**
	 * Query all.
	 * 
	 * @param page
	 * @param size
	 * @return List<ElasticSearchTestEntity>
	 */
	@GetMapping
	public @ResponseBody List<ElasticSearchTestEntity> findAll(@RequestParam(defaultValue = "0") Integer page,
			@RequestParam(defaultValue = "10") Integer size, HttpServletRequest request, HttpServletResponse response) {
		return elasticSearchTestService.findAll(page, size);
	}

	/**
	 * Query distinct data by name or email
	 * 
	 * @param name
	 * @param email
	 * @param page
	 * @param size
	 * @return List<ElasticSearchTestEntity>
	 */
	@GetMapping("/find")
	public @ResponseBody List<ElasticSearchTestEntity> find(@RequestParam String name, @RequestParam String email,
			@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size,
			HttpServletRequest request, HttpServletResponse response) {
		return elasticSearchTestService.find(name, email, page, size);
	}
}
