package top.jalo.commons.webservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import top.jalo.commons.webservice.model.WebserviceTest;
import top.jalo.commons.webservice.service.JpaGenericService;
import top.jalo.commons.webservice.service.UserService;

/**
 * Controller : Test UserController which extend GenericService<E, M, EID extends Serializable, MID extends Serializable>.
 *
 * @Author JALO
 *
 */
@RestController
@RequestMapping("/user")
public class UserController extends JpaGenericController<WebserviceTest, Long> {

	@Autowired
	private UserService userService;
	
	@Override
	protected JpaGenericService<?, WebserviceTest, ?, Long> getService() {
		return userService;
	}
}
