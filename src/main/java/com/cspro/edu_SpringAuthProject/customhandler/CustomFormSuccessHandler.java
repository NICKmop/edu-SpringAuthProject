package com.cspro.edu_SpringAuthProject.customhandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.cspro.edu_SpringAuthProject.auth.service.RefreshTokenService;
import com.cspro.edu_SpringAuthProject.util.CookieUtil;
import com.cspro.edu_SpringAuthProject.util.JWTUtil;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author CS
 * 로그인 후 JWT 토큰 발급 
 * access -> 헤더 ::::
 * refresh -> 쿠키 ::::
 */
@RequiredArgsConstructor
@Slf4j
public class CustomFormSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{
	private final JWTUtil jwtUtil;
	private final RefreshTokenService refreshTokenService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws StreamWriteException, DatabindException, IOException {
		// create JWT
		String username = authentication.getName();
		String role = authentication.getAuthorities().iterator().next().getAuthority();
		
		// access 60 * 10 * 1000L: 1시간
        String access = jwtUtil.createJwt("access", username, role, 60 * 10 * 1000L);
        response.setHeader("access", access);
        
		// refresh
		Integer expires = 24 * 60 * 60;
		String refresh = jwtUtil.createJwt("refresh", username, role, expires * 1000L);
		response.addCookie(CookieUtil.createCookie("refresh", refresh, expires));
		
//		log.info("CustomFormSuccessHandler header : {}", response.getHeader("access"));
		
		// refresh Token DB :::: SAVE
		refreshTokenService.saveRefresh(username, expires, refresh);

		// Log the access token
//	    log.info("CustomFormSuccessHandler: access token added to cookie");
		// Redirect to /main
	    getRedirectStrategy().sendRedirect(request, response, "/main");
	    
	    // JSON DATA 직렬 화
	    Map<String, String> responseData = new HashMap<>();
	    responseData.put("access", access);
	    responseData.put("refresh", refresh);

	    // Set response type and write JSON
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");

	    log.info("Writing JSON response: {}", responseData);
	    new ObjectMapper().writeValue(response.getWriter(), responseData);
	    
	}
}
