package com.appointment.booking.controller;

import com.appointment.booking.controller.dto.AppointmentRequest;
import com.appointment.booking.controller.dto.AppointmentResponse;
import com.appointment.booking.service.AppointmentService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    // CREATE Appointment
    @PostMapping
    public ResponseEntity<AppointmentResponse> createAppointment(
            @RequestBody AppointmentRequest request) {

        AppointmentResponse created = appointmentService.createAppointment(request);
        return ResponseEntity.ok(created);
    }

    // GET all appointments for a provider
    @GetMapping("/provider/{providerId}")
    public ResponseEntity<List<AppointmentResponse>> getAppointmentsForProvider(
            @PathVariable Long providerId) {

        return ResponseEntity.ok(appointmentService.getAppointmentsByProvider(providerId));
    }

    // GET all appointments for a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AppointmentResponse>> getAppointmentsForUser(
            @PathVariable Long userId) {

        return ResponseEntity.ok(appointmentService.getAppointmentsByUser(userId));
    }

    // UPDATE status (CONFIRM / CANCEL / COMPLETE)
    @PutMapping("/{appointmentId}/status")
    public ResponseEntity<AppointmentResponse> updateStatus(
            @PathVariable Long appointmentId,
            @RequestParam String status) {

        return ResponseEntity.ok(
                appointmentService.updateAppointmentStatus(appointmentId, status)
        );
    }

    // DELETE an appointment
    @DeleteMapping("/{appointmentId}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long appointmentId) {
        appointmentService.deleteAppointment(appointmentId);
        return ResponseEntity.noContent().build();
    }
}