package com.appointment.booking.service;

import com.appointment.booking.model.Schedule;
import com.appointment.booking.model.Provider;

import java.util.List;

public interface ScheduleService {

    Schedule addSchedule(Schedule schedule);

    List<Schedule> findByProvider(Provider provider);
}
