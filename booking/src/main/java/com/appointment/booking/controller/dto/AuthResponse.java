package com.appointment.booking.controller.dto;

import com.appointment.booking.model.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {

    private Long id;
    private String name;
    private String email;
    private String role;
    private String token;

    public static AuthResponse from(User user, String token) {
        return AuthResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .token(token)
                .build();
    }
}