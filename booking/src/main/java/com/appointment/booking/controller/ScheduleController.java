package com.appointment.booking.controller;

import com.appointment.booking.controller.dto.ScheduleRequest;
import com.appointment.booking.controller.dto.ScheduleResponse;
import com.appointment.booking.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.appointment.booking.model.User;
import com.appointment.booking.security.UserDetailsImpl;

import java.util.List;

@RestController
@RequestMapping("/provider/{providerId}/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    @Autowired
    private final ScheduleService scheduleService;

    // Create schedule for a provider
    @PostMapping
    public ResponseEntity<ScheduleResponse> createSchedule(
            @PathVariable Long providerId,
            @RequestBody ScheduleRequest scheduleRequest,
            @AuthenticationPrincipal UserDetailsImpl currentUser

    ) {
        User user = currentUser.getUser();

        ScheduleResponse createdSchedule = scheduleService.createSchedule(providerId, scheduleRequest, user);
        return ResponseEntity.ok(createdSchedule);
    }

    // Get ALL schedules of a provider
    @GetMapping
    public ResponseEntity<List<ScheduleResponse>> getProviderSchedules(
            @PathVariable Long providerId
    ) {
        return ResponseEntity.ok(scheduleService.findByProvider(providerId));
    }

    // Get schedule by ID
    /*
    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponse> getScheduleById(
            @PathVariable Long scheduleId
    ) {
        return ResponseEntity.ok(scheduleService.getScheduleById(scheduleId));
    }
    */

    // Update schedule
    @PutMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponse> updateSchedule(
            @PathVariable Long providerId,
            @PathVariable Long scheduleId,
            @RequestBody ScheduleRequest scheduleRequest,
            @AuthenticationPrincipal UserDetailsImpl currentUser

    ) {
        User user = currentUser.getUser();

        return ResponseEntity.ok(scheduleService.updateSchedule(providerId, scheduleId, scheduleRequest, user));
    }

    // Delete schedule
    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<String> deleteSchedule(
            @PathVariable Long providerId,
            @PathVariable Long scheduleId,
            @AuthenticationPrincipal UserDetailsImpl currentUser

    ) {
        User user = currentUser.getUser();

        scheduleService.deleteSchedule(providerId, scheduleId, user);
        return ResponseEntity.ok("Schedule deleted successfully.");
    }
}