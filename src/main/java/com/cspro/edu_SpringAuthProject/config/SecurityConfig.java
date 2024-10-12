package com.cspro.edu_SpringAuthProject.config;

import java.io.IOException;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.cspro.edu_SpringAuthProject.auth.mapper.RefreshRepository;
import com.cspro.edu_SpringAuthProject.auth.service.RefreshTokenService;
import com.cspro.edu_SpringAuthProject.customhandler.CustomFormSuccessHandler;
import com.cspro.edu_SpringAuthProject.customhandler.CustomLogoutFilter;
import com.cspro.edu_SpringAuthProject.util.JWTFilter;
import com.cspro.edu_SpringAuthProject.util.JWTUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;


@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    
	private final JWTUtil jwtUtil;
//    private final CustomOAuth2UserService customOAuth2UserService;
    private final RefreshTokenService refreshTokenService;
    private final RefreshRepository refreshRepository;
    
	@Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Bean
    public AuthenticationFailureHandler authenticationFailureHandler(){
        return new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                System.out.println("exception = " + exception);
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
            }
        };
    }
	
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	// not use : disable        
    	http
    		.httpBasic((basic) -> basic.disable())
    		.csrf((csrf) -> csrf.disable());
    	// authorization
        http.authorizeHttpRequests((auth) -> auth
        		.requestMatchers("/", "/login", "/signup").permitAll()
                .requestMatchers("/main", "/main/**").hasRole("ADMIN")
                .anyRequest().authenticated());
    	// form login    	
    	http
    		.formLogin((form) -> form
    				.loginPage("/login")
    				.loginProcessingUrl("/login-proc")
    				.usernameParameter("username")
    				.passwordParameter("password")
    				.successHandler(new CustomFormSuccessHandler(jwtUtil, refreshTokenService))
    				.failureHandler(authenticationFailureHandler())
    				.permitAll());
    	// logout
    	http
    		.logout((auth) -> auth
    				.logoutSuccessUrl("/")
    				.permitAll());
    	// cors
        http
                .cors((cors) -> cors.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration configuration = new CorsConfiguration();
                        configuration.setAllowedOrigins(Collections.singletonList("http://localhost:8080/"));
                        configuration.setAllowedMethods(Collections.singletonList("*"));
                        configuration.setAllowCredentials(true);
                        configuration.setAllowedHeaders(Collections.singletonList("*"));
                        configuration.setMaxAge(3600L);

                        configuration.setExposedHeaders(Collections.singletonList("access"));

                        return configuration;
                    }
                }));
        // 인가되지 않은 사용자에 대한 exception -> 프론트엔드로 코드 응답
        http.exceptionHandling((exception) ->
                exception
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        }));
        // jwt filter
        http
        	.addFilterAfter(new JWTFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);
        // custom logout filter
        http
        	.addFilterBefore(new CustomLogoutFilter(jwtUtil, refreshRepository), LogoutFilter.class);
        
        // session stateless
        http
        	.sessionManagement((session) -> session
        			.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        	
        return http.build();
    }
}
