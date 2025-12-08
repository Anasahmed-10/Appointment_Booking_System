package com.appointment.booking.service.impl;

import com.appointment.booking.controller.dto.RegisterRequest;
import com.appointment.booking.model.User;
import com.appointment.booking.model.UserRole;
import com.appointment.booking.repository.UserRepository;
import com.appointment.booking.service.AuthService;
import com.appointment.booking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;
   
    private final PasswordEncoder passwordEncoder;
  
    private final AuthenticationManager authenticationManager;
  
    private final UserService userService;

    @Override
    public User register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        //agr role provided nahi then set it as customer
        if (request.getRole() == null) {
            request.setRole(UserRole.CUSTOMER);
        }

        request.setPassword(passwordEncoder.encode(request.getPassword()));

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .role(request.getRole())
                .build();

        //User saved = userService.registerUser(user);
        User saved = userRepository.save(user);
        saved.setPassword(null); // nulling out password for safety "only in response"
        return saved;
    }

    @Override
    public User authenticate(String email, String rawPassword) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, rawPassword));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(null); //password expose na ho
        return user;
    }
}
