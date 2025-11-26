package com.appointment.booking.controller;

import com.appointment.booking.controller.dto.ServiceRequest;
import com.appointment.booking.controller.dto.ServiceResponse;
import com.appointment.booking.model.Provider;
import com.appointment.booking.model.ServiceEntity;
import com.appointment.booking.service.ProviderService;
import com.appointment.booking.service.ServiceEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
public class ServiceController {

    private final ServiceEntityService serviceEntityService;
    private final ProviderService providerService;

    // CREATE service
    @PostMapping
    public ResponseEntity<ServiceResponse> createService(@Valid @RequestBody ServiceRequest req) {
        Provider provider = providerService.findById(req.getProviderId())
                .orElseThrow(() -> new RuntimeException("Provider not found"));

        ServiceEntity service = ServiceEntity.builder()
                .name(req.getName())
                .description(req.getDescription())
                .duration(req.getDuration())
                .price(req.getPrice())
                .provider(provider)
                .build();

        ServiceEntity saved = serviceEntityService.createService(service);
        return ResponseEntity.ok(ServiceResponse.fromEntity(saved));
    }

    // GET service by ID
    @GetMapping("/{id}")
    public ResponseEntity<ServiceResponse> getServiceById(@PathVariable Long id) {
        return serviceEntityService.findById(id)
                .map(s -> ResponseEntity.ok(ServiceResponse.fromEntity(s)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // GET all services for a provider
    @GetMapping("/by-provider/{providerId}")
    public ResponseEntity<List<ServiceResponse>> getServicesByProvider(@PathVariable Long providerId) {
        Provider provider = providerService.findById(providerId)
                .orElseThrow(() -> new RuntimeException("Provider not found"));

        List<ServiceResponse> services = serviceEntityService.findByProvider(provider)
                .stream()
                .map(ServiceResponse::fromEntity)
                .toList();

        return ResponseEntity.ok(services);
    }

    // UPDATE service
    @PutMapping("/{id}")
    public ResponseEntity<ServiceResponse> updateService(
            @PathVariable Long id,
            @Valid @RequestBody ServiceRequest req) {

        ServiceEntity updated = serviceEntityService.updateService(id, req);
        return ResponseEntity.ok(ServiceResponse.fromEntity(updated));
    }

    // DELETE service
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        serviceEntityService.deleteService(id);
        return ResponseEntity.noContent().build();
    }
}
