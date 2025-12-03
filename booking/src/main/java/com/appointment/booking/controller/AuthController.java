package com.appointment.booking.controller;

import com.appointment.booking.controller.dto.LoginRequest;
import com.appointment.booking.controller.dto.LoginResponse;
import com.appointment.booking.controller.dto.RegisterRequest;
import com.appointment.booking.controller.dto.UserResponse;
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
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request) {
        User createdUser = authService.register(request);
        return ResponseEntity.ok(UserResponse.fromEntity(createdUser));
    }

    // 2. LOGIN AND RECEIVE JWT
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {

        User user = authService.authenticate(request.getEmail(), request.getPassword());

        String token = jwtUtil.generateToken(user);

        return ResponseEntity.ok(
                LoginResponse.builder()
                        .token(token)
                        .user(UserResponse.fromEntity(user))
                        .build()
        );
    }
}