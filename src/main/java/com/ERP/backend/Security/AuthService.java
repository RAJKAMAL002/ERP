package com.ERP.backend.Security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.ERP.backend.DTO.LoginRequestDTO;
import com.ERP.backend.DTO.LoginResponseDTO;
import com.ERP.backend.DTO.signupResponseDTO;
import com.ERP.backend.Entity.Users;
import com.ERP.backend.Repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	
	private final UserRepository userRepo;
	private final AuthenticationManager authenticationManager;
	private final AuthUtil authUtil;
	private final ModelMapper modelMapper;
	private final PasswordEncoder passwordEncoder;
	
	public LoginResponseDTO login(LoginRequestDTO loginRequestDTO){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequestDTO.getUsername(),
                    loginRequestDTO.getPassword()
                )
            );
        String username = authentication.getName();
        Users user = userRepo.findByUsername(username).orElseThrow();
        String token = authUtil.generateAccessToken(user);
		return new LoginResponseDTO(token, user.getUsername());
	}

	public signupResponseDTO signup(LoginRequestDTO signupRequestDTO) {
		Users user = userRepo.findByUsername(signupRequestDTO.getUsername()).orElse(null);
		if(user != null) throw new IllegalArgumentException("Username already exists");
		user = userRepo.save(Users.builder()
				.username(signupRequestDTO.getUsername())
				.password(passwordEncoder.encode(signupRequestDTO.getPassword()))
				.build()
		       );
		return modelMapper.map(user, signupResponseDTO.class);
	}
}
