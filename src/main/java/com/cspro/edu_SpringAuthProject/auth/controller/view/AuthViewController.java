package com.cspro.edu_SpringAuthProject.auth.controller.view;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class AuthViewController {
	
	@GetMapping("/main")
	public String MainView() {
		log.info("main PAge");
		return "main";
	}
	
	@GetMapping("/main/{id}")
	public String MainAnyView() {
		log.info("main PAge");
		return "main";
	}
	
	@GetMapping({"/", "/login"})
	public String LoginView() {
		return "login";
	}
	
	@GetMapping("/signup")
	public String SignUpView() {
		return "signup";
	}
}
