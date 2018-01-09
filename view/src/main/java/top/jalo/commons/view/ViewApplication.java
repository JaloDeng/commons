package top.jalo.commons.view;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import top.jalo.commons.security.SecurityApplication;

@SpringBootApplication
public class ViewApplication {

	public static void main(String[] args) {
		SpringApplication.run(new Object[] {SecurityApplication.class, ViewApplication.class}, args);
	}
}
