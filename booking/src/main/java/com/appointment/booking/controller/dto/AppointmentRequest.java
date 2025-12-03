package com.appointment.booking.controller.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AppointmentRequest {

    private Long userId;       // who is booking
    private Long providerId;   // which provider
    private Long serviceId;    // selected service
    private LocalDate date;    // booking date
    private LocalTime startTime;    // booking starting time
    private LocalTime endTime;    // booking ending time
}