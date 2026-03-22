package com.ERP.backend.Security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ERP.backend.Entity.Users;
import com.ERP.backend.Repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter{
	
	private final UserRepository userRepo;
	private final AuthUtil authUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		
		// Logging the Requests
		log.info("Incoming requests : {}", request.getRequestURI());
		
		final String requestHeaderToken = request.getHeader("Authorization");
		
		// If the token is null or is not starting with the bearer then their is some problem so return
		if(requestHeaderToken == null || !requestHeaderToken.startsWith("Bearer")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		String token = requestHeaderToken.split("Bearer ")[1];
		String username = authUtil.getUsernameFromToken(token);
		
		if(username == null || SecurityContextHolder.getContext().getAuthentication() == null) {
			Users user = userRepo.findByUsername(username).orElseThrow();
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
		}
		
		filterChain.doFilter(request, response);
		
	}

}
