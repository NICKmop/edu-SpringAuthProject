package com.cspro.edu_SpringAuthProject.auth.controller.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cspro.edu_SpringAuthProject.auth.object.dto.SignInDto;
import com.cspro.edu_SpringAuthProject.auth.service.AuthSignUpService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthApiController {
	private final AuthSignUpService authSignUpService;
	
	@PostMapping("/signup")
	public String signUpProc(SignInDto signInDto) {
		authSignUpService.signUp(signInDto);
		return String.format("sign user : %s ", signInDto.getUsername());
	}
}
