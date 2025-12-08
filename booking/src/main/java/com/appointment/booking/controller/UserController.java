package com.appointment.booking.controller;

import com.appointment.booking.model.User;

import com.appointment.booking.model.UserRole;
import com.appointment.booking.security.UserDetailsImpl;
import com.appointment.booking.service.UserService;
import com.appointment.booking.controller.dto.RegisterRequest;
import com.appointment.booking.controller.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import jakarta.validation.Valid;

import static com.appointment.booking.model.User.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Validated
public class UserController {

   
    private final UserService userService;

    /**
     * Register a new user. Default role = CUSTOMER unless caller provides otherwise.
     */
    /* 
    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody RegisterRequest req) {
        User user = builder()
                .name(req.getName())
                .email(req.getEmail())
                .password(req.getPassword()) // password should be encoded in service layer
                .phone(req.getPhone())
                .address(req.getAddress())
                .role(req.getRole() != null ? req.getRole() : UserRole.CUSTOMER)
                .build();

        User saved = userService.registerUser(user);

        UserResponse resp = new UserResponse(saved.getId(), saved.getName(), saved.getEmail(),
                saved.getPhone(), saved.getAddress(), saved.getRole().name());

        return ResponseEntity.ok(resp);
    }
*/


    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userService.findById(userDetails.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserResponse resp = new UserResponse(user.getId(), user.getEmail(),
                 user.getRole().name());
//, user.getName(),  user.getPhone(), user.getAddress(),
        return ResponseEntity.ok(resp);
    }
    /**
     * Get user by id (public endpoint for now — later protect with roles)
     */
   

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        return userService.findById(id)
                .map(u -> ResponseEntity.ok(new UserResponse(u.getId(), u.getEmail(),
                         u.getRole().name())))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    /**
     * Get user by email (useful for testing)
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/by-email")
    public ResponseEntity<UserResponse> getUserByEmail(@RequestParam String email) {
        return userService.findByEmail(email)
                .map(u -> ResponseEntity.ok(new UserResponse(u.getId(), u.getEmail(),
                        u.getRole().name())))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
