package com.ERP.backend.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ERP.backend.DTO.LoginRequestDTO;
import com.ERP.backend.DTO.LoginResponseDTO;
import com.ERP.backend.DTO.signupResponseDTO;
import com.ERP.backend.Security.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        LoginResponseDTO response = authService.login(loginRequestDTO);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/signup")
    public ResponseEntity<signupResponseDTO> signup(@RequestBody LoginRequestDTO signupRequestDTO) {
        signupResponseDTO response = authService.signup(signupRequestDTO);
        return ResponseEntity.ok(response);
    }
}