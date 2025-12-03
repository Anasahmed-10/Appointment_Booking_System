package com.appointment.booking.service;

import com.appointment.booking.controller.dto.RegisterRequest;
import com.appointment.booking.model.User;

public interface AuthService {

    /**
     * Register a new user and return the created User.
     * - Should validate duplicate email, encode password, set default role if needed.
     */
    User register(RegisterRequest request);

    /**
     * Authenticate a user with email & password.
     * Should throw an exception if authentication fails.
     * Returns the authenticated User (without password in responses).
     */
    User authenticate(String email, String rawPassword);
}