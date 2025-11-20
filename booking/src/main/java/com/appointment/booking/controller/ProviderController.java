package com.appointment.booking.controller;

import com.appointment.booking.controller.dto.ProviderRequest;
import com.appointment.booking.controller.dto.ProviderResponse;
import com.appointment.booking.model.Provider;
import com.appointment.booking.model.User;
import com.appointment.booking.service.ProviderService;
import com.appointment.booking.service.UserService;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/providers")
@RequiredArgsConstructor

public class ProviderController {

    private final ProviderService providerService;

    private final UserService userService;
    // CREATE provider
     @PostMapping
    public ResponseEntity<ProviderResponse> createProvider(@Valid @RequestBody ProviderRequest req) {
        User user = userService.findById(req.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Provider provider = Provider.builder()
                .user(user)
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
            @Valid @RequestBody ProviderRequest req) {

        Provider updated = providerService.updateProvider(id, req);
        return ResponseEntity.ok(ProviderResponse.fromEntity(updated));
    }

    // DELETE provider
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProvider(@PathVariable Long id) {
        providerService.deleteProvider(id);
        return ResponseEntity.noContent().build();
    }
}