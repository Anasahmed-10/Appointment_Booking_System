package com.appointment.booking.controller;

import com.appointment.booking.controller.dto.ProviderRequest;
import com.appointment.booking.controller.dto.ProviderResponse;
import com.appointment.booking.model.Provider;
import com.appointment.booking.model.User;
import com.appointment.booking.security.UserDetailsImpl;
import com.appointment.booking.service.ProviderService;
import com.appointment.booking.service.UserService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/providers")
@RequiredArgsConstructor

public class ProviderController {

    private final ProviderService providerService;
  
    /*
    private final UserService userService;
    
        User user = userService.findById(req.getUserId())
            .orElseThrow(() -> new RuntimeException("User not found"));

                */

    @PostMapping
    public ResponseEntity<ProviderResponse> createProvider(
            @Valid @RequestBody ProviderRequest req,
            @AuthenticationPrincipal UserDetailsImpl currentUser) {
        
        User user = currentUser.getUser();

        Provider provider = Provider.builder()
                .user(user) // get from JWT token
                .specialization(req.getSpecialization())
                .description(req.getDescription())
                .contactInfo(req.getContactInfo())
                .build();

        Provider saved = providerService.createProvider(provider);
        return ResponseEntity.ok(ProviderResponse.fromEntity(saved));
    }



    // GET provider by ID
    @GetMapping("/{id}")
    public ResponseEntity<ProviderResponse> getProviderById(@PathVariable Long id) {
        return providerService.findById(id)
                .map(provider -> ResponseEntity.ok(ProviderResponse.fromEntity(provider)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    // GET all providers
    @GetMapping
    public ResponseEntity<List<ProviderResponse>> getAllProviders() {
        List<ProviderResponse> providers = providerService.findAll()
                .stream()
                .map(ProviderResponse::fromEntity)
                .toList();

        return ResponseEntity.ok(providers);
    }

    // UPDATE provider
    @PutMapping("/{id}")
    public ResponseEntity<ProviderResponse> updateProvider(
            @PathVariable Long id,
            @Valid @RequestBody ProviderRequest req,
            @AuthenticationPrincipal UserDetailsImpl currentUser) {

            User user = currentUser.getUser();
            Provider updated = providerService.updateProvider(id, req, user);
        return ResponseEntity.ok(ProviderResponse.fromEntity(updated));
    }

   // DELETE provider
   @DeleteMapping("/{id}")
   public ResponseEntity<Void> deleteProvider(
           @PathVariable Long id,
           @AuthenticationPrincipal UserDetailsImpl currentUser) {

        
    
        User user = currentUser.getUser();
        
       providerService.deleteProvider(id, user);
       return ResponseEntity.noContent().build();
   }

}