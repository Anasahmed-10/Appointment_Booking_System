package com.appointment.booking.service;

import com.appointment.booking.controller.dto.ScheduleRequest;
import com.appointment.booking.controller.dto.ScheduleResponse;
import com.appointment.booking.model.User;

import java.util.List;

public interface ScheduleService {

    ScheduleResponse createSchedule(Long providerId, ScheduleRequest scheduleRequest, User currentUser);

    List<ScheduleResponse> findByProvider(Long providerId);

    ScheduleResponse updateSchedule(Long providerId, Long scheduleId, ScheduleRequest scheduleRequest, User currentUser);

    void deleteSchedule(Long providerId, Long scheduleId, User currentUser);
}

