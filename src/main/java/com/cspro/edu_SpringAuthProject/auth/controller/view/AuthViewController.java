package com.cspro.edu_SpringAuthProject.auth.controller.view;

import org.springframework.http.HttpMethod;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class AuthViewController {
	
	@RequestMapping(value = {"/main", "/main/**"})
	public String MainAnyView(HttpMethod method, HttpServletRequest request, HttpServletResponse response) {
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
