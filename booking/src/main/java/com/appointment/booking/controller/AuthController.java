package com.appointment.booking.controller;

import com.appointment.booking.controller.dto.LoginRequest;
import com.appointment.booking.controller.dto.LoginResponse;
import com.appointment.booking.controller.dto.RegisterRequest;
import com.appointment.booking.controller.dto.UserResponse;
import com.appointment.booking.controller.dto.AuthResponse;
import com.appointment.booking.model.User;
import com.appointment.booking.security.UserDetailsImpl;
import com.appointment.booking.service.AuthService;
import com.appointment.booking.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  
    private final AuthService authService;

    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        User createdUser = authService.register(request);
        String token = jwtUtil.generateToken(createdUser);
        return ResponseEntity.ok(AuthResponse.from(createdUser, token));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {

        User authenticatedUser = authService.authenticate(request.getEmail(), request.getPassword());

        String token = jwtUtil.generateToken(authenticatedUser);

        return ResponseEntity.ok(AuthResponse.from(authenticatedUser, token));
    }
    
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(@AuthenticationPrincipal UserDetailsImpl currentUser) {
        User user = currentUser.getUser();
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(UserResponse.fromEntity(user));
}
}