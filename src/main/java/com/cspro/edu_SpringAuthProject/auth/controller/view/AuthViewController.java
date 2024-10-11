package com.cspro.edu_SpringAuthProject.auth.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthViewController {
	
	@GetMapping("/main")
	public String MainView() {
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
