package com.ERP.backend.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {
	
	public final PasswordEncoder passwordEncoder;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
		httpSecurity
		.csrf(csrfConfig -> csrfConfig.disable())
		.sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.authorizeHttpRequests(auth -> auth
				.requestMatchers("/erp/api/purchases/**").hasRole("ADMIN")
				.requestMatchers("/erp/api/product/**", "/auth/**").permitAll()
				.requestMatchers("/erp/api/**").authenticated()
				);
//		.formLogin(Customizer.withDefaults());
		return httpSecurity.build();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
	    return config.getAuthenticationManager();
	}
}
