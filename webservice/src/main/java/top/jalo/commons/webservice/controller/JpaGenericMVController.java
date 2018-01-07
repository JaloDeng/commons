package top.jalo.commons.webservice.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Generic Controller for JPA which all methods return ModelAndView.
 *
 * @Author JALO
 *
 */
public class JpaGenericMVController {

	@GetMapping
	public ModelAndView findAll(@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer size, @RequestParam(required = false) String sorts,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		return new ModelAndView("");
	}
	
}
