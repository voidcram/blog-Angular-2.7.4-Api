package com.marcos.blog.controllers;

import com.marcos.blog.payload.requests.LoginRequest;
import com.marcos.blog.payload.requests.UserRequest;
import com.marcos.blog.payload.response.AuthResponse;
import com.marcos.blog.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid UserRequest registerRequest) {
        return authService.register(registerRequest);
    }
}
