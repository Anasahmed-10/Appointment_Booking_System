package com.appointment.booking.service.impl;

import com.appointment.booking.model.Schedule;
import com.appointment.booking.model.Provider;
import com.appointment.booking.repository.ScheduleRepository;
import com.appointment.booking.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Override
    public Schedule addSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    @Override
    public List<Schedule> findByProvider(Provider provider) {
        return scheduleRepository.findByProvider(provider);
    }
}