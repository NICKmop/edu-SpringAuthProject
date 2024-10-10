package com.cspro.edu_SpringAuthProject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;


// jwt 인가 발급 and bcrypt passwordEncoder로 변경 필요
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf((csrf) -> csrf.disable());
        http
            .authorizeHttpRequests((auth) -> auth
                .anyRequest().permitAll());
//        http
//                .formLogin((form) -> form.de);
        return http.build();
    }
    
//    추후 보안 강화시 OAUTH2 적용 하는 방향으로
//    @Bean
//    @Order(Ordered.HIGHEST_PRECEDENCE)
//    public SecurityFilterChain authorizationServer(HttpSecurity http) throws Exception {
//
//        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
//
//        http
//                .getConfigurer(OAuth2AuthorizationServerConfigurer.class);
//                        
//        http
//                .exceptionHandling((exceptions) -> exceptions
//                        .defaultAuthenticationEntryPointFor(
//                                new LoginUrlAuthenticationEntryPoint("/login"),
//                                new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
//                        )
//                );
//
//        return http.build();
//    }
    
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
//    @Bean
//    public AuthorizationServerSettings authorizationServerSettings() {
//
//        return AuthorizationServerSettings.builder()
//                .issuer("http://localhost:9000")
//                .authorizationEndpoint("/oauth2/v1/authorize")
//                .tokenEndpoint("/oauth2/v1/token")
//                .tokenIntrospectionEndpoint("/oauth2/v1/introspect") // 토큰 상태
//                .tokenRevocationEndpoint("/oauth2/v1/revoke") // 토큰 폐기 RFC 7009
//                .jwkSetEndpoint("/oauth2/v1/jwks") // 공개키 확인
//                .oidcLogoutEndpoint("/connect/v1/logout")
//                .oidcUserInfoEndpoint("/connect/v1/userinfo") // 리소스 서버 유저 정보 연관
//                .oidcClientRegistrationEndpoint("/connect/v1/register") // OAuth2 사용 신청
//                .build();
//    }
}
