package com.ERP.backend.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class WebSecurityConfig {
	
	public final PasswordEncoder passwordEncoder;
	public final JwtAuthFilter jwtAuthFilter;
	public final OAuth2SuccessHandler oAuth2SuccessHandler;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
	    httpSecurity
	        .cors(cors -> {})
	        .csrf(csrfConfig -> csrfConfig.disable())
	        .sessionManagement(sessionConfig -> 
	        sessionConfig.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
	        
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers("/erp/api/product/**", "/auth/**").permitAll()
	            .anyRequest().authenticated()
	        )
	        
	        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
	        
//	        .oauth2Login(oAuth2 -> oAuth2
//	            .failureHandler((request, response, exception) -> {
//	                log.error("Oauth2 Error {}", exception);
//	            })
//	            .successHandler(oAuth2SuccessHandler)
//	        );

	    return httpSecurity.build();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
	    return config.getAuthenticationManager();
	}
}
