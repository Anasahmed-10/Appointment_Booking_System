package com.appointment.booking.controller;

import com.appointment.booking.controller.dto.LoginRequest;
import com.appointment.booking.controller.dto.LoginResponse;
import com.appointment.booking.controller.dto.RegisterRequest;
import com.appointment.booking.controller.dto.UserResponse;
import com.appointment.booking.controller.dto.AuthResponse;
import com.appointment.booking.model.User;
import com.appointment.booking.service.AuthService;
import com.appointment.booking.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    // 1. REGISTER NEW USER
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        User createdUser = authService.register(request);
        String token = jwtUtil.generateToken(createdUser);
        return ResponseEntity.ok(AuthResponse.from(createdUser, token));
    }

    // 2. LOGIN AND RECEIVE JWT
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {

        User authenticatedUser = authService.authenticate(request.getEmail(), request.getPassword());

        String token = jwtUtil.generateToken(authenticatedUser);

        return ResponseEntity.ok(AuthResponse.from(authenticatedUser, token));
    }
}