package top.jalo.commons.view.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller : view
 *
 * @Author JALO
 *
 */
@RestController
@RequestMapping("/test")
public class ViewController {

	@GetMapping
	public ModelAndView test(Model model, HttpServletRequest request, HttpServletResponse response) {
		model.addAttribute("title", "Test Page");
		return new ModelAndView("view/test", "result", model);
	}
}
