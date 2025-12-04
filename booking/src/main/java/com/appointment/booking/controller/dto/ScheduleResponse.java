package com.appointment.booking.controller.dto;

import lombok.*;

import java.time.LocalTime;
import com.appointment.booking.model.DaysOfWeek;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleResponse {

    private Long id;
    private Long providerId;

    private DaysOfWeek dayOfWeek;
    private LocalTime availableFrom;
    private LocalTime availableTo;
}