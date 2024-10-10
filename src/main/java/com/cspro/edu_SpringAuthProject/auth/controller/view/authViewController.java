package com.cspro.edu_SpringAuthProject.auth.controller.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cspro.edu_SpringAuthProject.auth.domain.UserDTO;
import com.cspro.edu_SpringAuthProject.auth.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class authViewController {
    
	@Autowired
	private final UserService userService;
	
	@GetMapping("/signup")
    public String joinPage() {

        return "signup";
    }

    @PostMapping("/join")
    @ResponseBody
    public String join(UserDTO dto) {
        userService.sign(dto);
        return "ok";
    }
}
