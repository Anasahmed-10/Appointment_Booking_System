package com.appointment.booking.controller;

import com.appointment.booking.controller.dto.AppointmentRequest;
import com.appointment.booking.controller.dto.AppointmentResponse;
import com.appointment.booking.service.AppointmentService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import com.appointment.booking.model.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    @Autowired
    private final AppointmentService appointmentService;

    // CREATE Appointment
    @PostMapping
    public ResponseEntity<AppointmentResponse> createAppointment(
            @RequestBody AppointmentRequest request,
            @AuthenticationPrincipal User currentUser) {

        AppointmentResponse created = appointmentService.createAppointment(request, currentUser);
        return ResponseEntity.ok(created);
    }

    // GET all appointments for a provider
    @GetMapping("/provider/{providerId}")
    public ResponseEntity<List<AppointmentResponse>> getAppointmentsForProvider(
            @PathVariable Long providerId,
            @AuthenticationPrincipal User currentUser) {

        return ResponseEntity.ok(appointmentService.getAppointmentsByProvider(providerId, currentUser));
    }

    // GET all appointments for a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AppointmentResponse>> getAppointmentsForUser(
            @PathVariable Long userId,
            @AuthenticationPrincipal User currentUser) {

        return ResponseEntity.ok(appointmentService.getAppointmentsByUser(userId, currentUser));
    }

    // UPDATE status (CONFIRM / CANCEL / COMPLETE)
    @PutMapping("/{appointmentId}/status")
    public ResponseEntity<AppointmentResponse> updateStatus(
            @PathVariable Long appointmentId,
            @RequestParam String status,
            @AuthenticationPrincipal User currentUser) {

        return ResponseEntity.ok(
                appointmentService.updateAppointmentStatus(appointmentId, status, currentUser)
        );
    }

    // DELETE an appointment
    @DeleteMapping("/{appointmentId}")
    public ResponseEntity<Void> deleteAppointment(
        @PathVariable Long appointmentId,
        @AuthenticationPrincipal User currentUser  ) {
        appointmentService.deleteAppointment(appointmentId, currentUser);
        return ResponseEntity.noContent().build();
    }
}