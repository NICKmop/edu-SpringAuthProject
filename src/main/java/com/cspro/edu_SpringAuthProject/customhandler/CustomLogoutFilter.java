package com.cspro.edu_SpringAuthProject.customhandler;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.web.filter.GenericFilterBean;

import com.cspro.edu_SpringAuthProject.auth.mapper.RefreshRepository;
import com.cspro.edu_SpringAuthProject.util.CookieUtil;
import com.cspro.edu_SpringAuthProject.util.JWTUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class CustomLogoutFilter extends GenericFilterBean{
	
	private final JWTUtil jwtUtil;
	private final RefreshRepository refreshRepository;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
	}
	
	private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		String requestURI = request.getRequestURI();
		
		// req url check
		if(!requestURI.matches("^\\/logout$")) {
			filterChain.doFilter(request, response);
			return;
		}
		// method check
		String requestMethod = request.getMethod();
		
		if(!requestMethod.equals("POST")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		// refresh Toekn validation ( 유효성 )
		String refresh = null;
		Cookie[] cookies = request.getCookies();
		
		refresh = Arrays.stream(cookies).filter((cookie) -> cookie.getName().equals("refresh"))
				.findFirst().get().getValue();
		
		// refresh token null -> wrong request
		if(refresh == null) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		String category = jwtUtil.getCategory(refresh);
		
		// not refresh token
		if(!category.equals("refresh")) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		Boolean isExist = refreshRepository.existsByRefresh(refresh);
		
		// not found DataBase
		if(!isExist) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		// logout user
		refreshRepository.deleteByRefresh(refresh);
		
		Cookie cookie = CookieUtil.createCookie("refresh", null, 0);
		response.addCookie(cookie);
		response.setStatus(HttpServletResponse.SC_OK);
	}
}
