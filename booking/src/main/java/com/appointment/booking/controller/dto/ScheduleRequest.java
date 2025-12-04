package com.appointment.booking.controller.dto;

import java.time.LocalTime;
import com.appointment.booking.model.DaysOfWeek;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleRequest {
    private DaysOfWeek dayOfWeek;
    private LocalTime availableFrom;
    private LocalTime availableTo;
}