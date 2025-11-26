package com.appointment.booking.service;

import com.appointment.booking.controller.dto.ScheduleRequest;
import com.appointment.booking.controller.dto.ScheduleResponse;
import com.appointment.booking.model.Schedule;
import com.appointment.booking.model.Provider;

import java.util.List;

public interface ScheduleService {

    ScheduleResponse createSchedule(Long providerId, ScheduleRequest scheduleRequest);

    List<ScheduleResponse> findByProvider(Long providerId);

    ScheduleResponse updateSchedule(Long providerId, Long scheduleId, ScheduleRequest scheduleRequest);

    void deleteSchedule(Long providerId, Long scheduleId);
}

