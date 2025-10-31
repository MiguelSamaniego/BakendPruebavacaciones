package com.example.vacaciones.controller;

import com.example.vacaciones.dto.LoginRequest;
import com.example.vacaciones.dto.LoginResponse;
import com.example.vacaciones.dto.RegisterRequest;
import com.example.vacaciones.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest req) {
        return ResponseEntity.ok(authService.login(req));
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@RequestBody RegisterRequest req) {
        LoginResponse resp = authService.register(req);
        return ResponseEntity.ok(resp);
    }
}
