package com.fescode.controller;

import com.fescode.dto.request.AuthRequest;
import com.fescode.dto.request.RegisterRequest;
import com.fescode.dto.response.AuthResponse;
import com.fescode.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

     private final AuthService authService;

     public AuthController(AuthService authService) {
        this.authService = authService; // Problemas con ejecutar por anotacion @AllArgsConstructor asi que mejor ponerlo manualmente
     }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
