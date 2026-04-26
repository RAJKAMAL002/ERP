package com.ERP.backend.Security;

import org.springframework.stereotype.Component;

import com.ERP.backend.Constants.AuthProviderType;
import com.ERP.backend.Entity.Users;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Component
@Slf4j
public class AuthUtil {
	
	@Value("${jwt.secretKey}")
	private String JwtSecretKey;
	
	private SecretKey getSecretkey() {
		return Keys.hmacShaKeyFor(JwtSecretKey.getBytes(StandardCharsets.UTF_8));
	}
	
	String generateAccessToken(Users users) {
		return Jwts.builder()
				.subject(users.getUsername())
				.claim("UserId", users.getId().toString())
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + 1000*60*10))
				.signWith(getSecretkey())
				.compact();
	}
	
	String getUsernameFromToken(String token) {
		Claims claims =  Jwts.parser()
						  .verifyWith(getSecretkey())
						  .build()
						  .parseSignedClaims(token)
						  .getPayload();
		
		return claims.getSubject();
	}
	
	public AuthProviderType getProviderTypeFromRegistrationId(String registrationId) {
		return switch (registrationId.toLowerCase()) {
			case "google"   -> AuthProviderType.GOOGLE;
			case "github"   -> AuthProviderType.GITHUB;
			case "facebook" -> AuthProviderType.FACEBOOK;
			default         -> throw new IllegalArgumentException("Unsupported OAuth2 Provider : " + registrationId);
		};
	}

	public String determineProviderIdFromOAuth2User(OAuth2User oAuth2User, String registrationId) {
		String providerId = switch(registrationId.toLowerCase()) {
				case "google" -> oAuth2User.getAttribute("sub");
				case "github" -> oAuth2User.getAttribute("id").toString();
				default -> {
					log.error("Unsupported OAuth2 Provider : {}" + registrationId);
					throw new IllegalArgumentException("Unsupported OAuth2 Provider : " + registrationId);
				}
		};
		
		if(providerId == null || providerId.isBlank()) {
			log.error("Unable to determine providerId for provider : {}" + registrationId);
			throw new IllegalArgumentException("Unable to determine providerId for provider : {}" + registrationId);
		}
		
		return providerId;
	}
	
	public String determineUsernameFromOAuth2User(OAuth2User oAuth2User, String registrationId, String providerId) {
		String email = oAuth2User.getAttribute("email");
		if(email != null && !email.isBlank()) {
			return email;
		}
		return switch (registrationId.toLowerCase()) {
				case "google" -> oAuth2User.getAttribute("sub");
				case "github" -> oAuth2User.getAttribute("login");
				default -> providerId;
		};
	}
}
