package com.appointment.booking.service;

import com.appointment.booking.model.User;
import java.util.Optional;

public interface UserService {

    User registerUser(User user);

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);
}