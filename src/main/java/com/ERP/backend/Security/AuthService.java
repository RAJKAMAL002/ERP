package com.ERP.backend.Security;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.stereotype.Service;

import com.ERP.backend.Constants.AuthProviderType;
import com.ERP.backend.DTO.LoginRequestDTO;
import com.ERP.backend.DTO.LoginResponseDTO;
import com.ERP.backend.DTO.signupResponseDTO;
import com.ERP.backend.Entity.Users;
import com.ERP.backend.Repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	
	private final UserRepository userRepo;
	private final AuthenticationConfiguration config;
	private final AuthUtil authUtil;
	private final ModelMapper modelMapper;
	private final PasswordEncoder passwordEncoder;
	
	public LoginResponseDTO login(LoginRequestDTO loginRequestDTO){
        Authentication authentication = config.getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequestDTO.getUsername(),
                    loginRequestDTO.getPassword()
                )
            );
        String username = authentication.getName();
        Users user = userRepo.findByUsername(username).orElseThrow();
        String token = authUtil.generateAccessToken(user);
        System.out.println(token);
		return new LoginResponseDTO(token, user.getUsername());
	}
	
	public Users signUpInternal(LoginRequestDTO signupRequestDTO, AuthProviderType authProviderType, String providerId) {
		Users user = userRepo.findByUsername(signupRequestDTO.getUsername()).orElse(null);
		if(user != null) throw new IllegalArgumentException("Username already exists");
		user =  Users.builder()
				.username(signupRequestDTO.getUsername())
				.providerId(providerId)
				.providerType(authProviderType)
				.build();
		
			user.setPassword(passwordEncoder.encode(signupRequestDTO.getPassword()));
		
		return userRepo.save(user);
	}

	public signupResponseDTO signup(LoginRequestDTO signupRequestDTO, AuthProviderType authProviderType, String providerId) {
		Users user = signUpInternal(signupRequestDTO, authProviderType, providerId);
		return new signupResponseDTO(user.getId(), user.getUsername());
	}
	
	@Transactional
	public ResponseEntity<LoginResponseDTO> handleOAuth2LoginRequest(OAuth2User oAuth2User, String registrationId) {

	    AuthProviderType authProviderType = authUtil.getProviderTypeFromRegistrationId(registrationId);
	    String providerId = authUtil.determineProviderIdFromOAuth2User(oAuth2User, registrationId);
	    String email = oAuth2User.getAttribute("email");

	    Users user = userRepo.findByProviderIdAndProviderType(providerId, authProviderType).orElse(null);

	    if (user == null) {
	        Users emailUser = (email != null) ? userRepo.findByUsername(email).orElse(null) : null;

	        if (emailUser != null) {
	            // ✅ LINK ACCOUNT instead of throwing error
	            emailUser.setProviderId(providerId);
	            emailUser.setProviderType(authProviderType);
	            user = userRepo.save(emailUser);
	        } else {
	            // ✅ New user signup
	            String username = authUtil.determineUsernameFromOAuth2User(oAuth2User, registrationId, providerId);
	            user = signUpInternal(new LoginRequestDTO(username, null), authProviderType, providerId);

	            user.setProviderId(providerId);
	            user.setProviderType(authProviderType);
	            userRepo.save(user);
	        }
	    }

	    // Optional: Update email if changed
	    if (email != null && !email.isBlank() && !email.equals(user.getUsername())) {
	        user.setUsername(email);
	        userRepo.save(user);
	    }

	    LoginResponseDTO loginResponseDTO =
	            new LoginResponseDTO(authUtil.generateAccessToken(user), user.getUsername());

	    return ResponseEntity.ok(loginResponseDTO);
	}
}
