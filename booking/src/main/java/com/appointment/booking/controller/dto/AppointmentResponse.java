package com.appointment.booking.controller.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class AppointmentResponse {

    private Long id;

    private Long userId;
    private Long providerId;
    private Long serviceId;

    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    private String status;
    private LocalDateTime createdAt;
}