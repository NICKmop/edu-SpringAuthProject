package com.cspro.edu_SpringAuthProject.util;

import java.io.IOException;
import java.util.Enumeration;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cspro.edu_SpringAuthProject.auth.entity.UserEntity;
import com.cspro.edu_SpringAuthProject.auth.object.dto.CustomUserDetails;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author USER
 * TOKEN 정보가 이미 있는 경우..
 * 내부에서 사용할 authentication 정보를 SET
 *
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JWTFilter extends OncePerRequestFilter {
	
	private final JWTUtil jwtUtil;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
	    String accessToken = null;
	    Cookie[] cookies = request.getCookies();
	    if (cookies != null) {
	        for (Cookie cookie : cookies) {
	            if ("refresh".equals(cookie.getName())) {
	                accessToken = cookie.getValue();
	            }
	        }
	    }
		if(accessToken == null) {
			filterChain.doFilter(request, response);
			return;
		}
		
		// token expired check
		try {
			jwtUtil.isExpired(accessToken);
		}catch (Exception e) {
			// TODO: handle exception
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
		    
	    if (accessToken != null ) {
	        // Proceed with JWT token processing
	        String username = jwtUtil.getUsername(accessToken);
	        String role = jwtUtil.getRole(accessToken);

	        // Set authentication in the SecurityContext
	        UserEntity userPrincipal = UserEntity.builder()
	                .username(username)
	                .role(role)
	                .password("password")
	                .build();

	        CustomUserDetails customUserDetails = new CustomUserDetails(userPrincipal);
	        UsernamePasswordAuthenticationToken authToken =
	                new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
	        SecurityContextHolder.getContext().setAuthentication(authToken);
	    }

	    filterChain.doFilter(request, response);
	}
	
//	->>>> header 원인 찾아야함 
//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
//		String access = null;
//		access = request.getHeader("access");
//		log.info("access : {}", access);
//		// access = "eyJhbGciOiJIUzI1NiJ9.eyJjYXRlZ29yeSI6ImFjY2VzcyIsInVzZXJuYW1lIjoidGVzdCIsInJvbGUiOiJST0xFX0FETUlOIiwiaWF0IjoxNzI4NzMzNDU5LCJleHAiOjE3Mjg3MzQwNTl9.hhf5PXx7hrWPEvLxnEuPRyJKHvJfoDLH0jHk2NvM1XI";
//		
//		// token null check
//		if(access == null) {
//			filterChain.doFilter(request, response);
//			return;
//		}
//		
//		// token expired check
//		try {
//			jwtUtil.isExpired(access);
//		}catch (Exception e) {
//			// TODO: handle exception
//			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//		}
//		
//		// category : access check		
//		String category = jwtUtil.getCategory(access);
//		
//		// not access token
//		if(!category.equals("access")) {
//			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//			return;
//		}
//		
//		String username = jwtUtil.getUsername(access);
//		String role = jwtUtil.getRole(access);
//		
//		UserEntity userPrincipal = UserEntity.builder()
//				.username(username)
//				.role(role)
//				.password("password")
//				.build();
//		
//		CustomUserDetails customUserDetails = new CustomUserDetails(userPrincipal);
//		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
//		SecurityContextHolder.getContext().setAuthentication(authToken);
//		
//		filterChain.doFilter(request, response);
//	}
}
