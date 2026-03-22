package com.ERP.backend.Security;

import org.springframework.stereotype.Component;

import com.ERP.backend.Entity.Users;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;

@Component
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
}
