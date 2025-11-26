package com.appointment.booking.controller.dto;

import lombok.Data;
import java.time.LocalTime;
import com.appointment.booking.model.DaysOfWeek;

@Data
public class ScheduleRequest {
    private DaysOfWeek dayOfWeek;
    private LocalTime availableFrom;
    private LocalTime availableTo;
}