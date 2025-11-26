package com.appointment.booking.service.impl;

import com.appointment.booking.controller.dto.ScheduleResponse;
import com.appointment.booking.model.Schedule;
import com.appointment.booking.model.Provider;
import com.appointment.booking.repository.ProviderRepository;
import com.appointment.booking.repository.ScheduleRepository;
import com.appointment.booking.service.ScheduleService;
import com.appointment.booking.controller.dto.ScheduleRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ProviderRepository providerRepository;

    @Override
    public ScheduleResponse createSchedule(Long providerId, ScheduleRequest scheduleRequest) {
        Provider provider = providerRepository.findById(providerId)
                .orElseThrow(() -> new RuntimeException("Provider not found"));

        Schedule schedule = toEntity(scheduleRequest, provider);
        Schedule saved = scheduleRepository.save(schedule);

        return toResponseDTO(saved);
    }



    @Override
    public List<ScheduleResponse> findByProvider(Long providerId) {
       List<Schedule> schedules = scheduleRepository.findByProvideId(providerId);
        return schedules.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }


    @Override
    public ScheduleResponse updateSchedule(Long providerId, Long scheduleId, ScheduleRequest scheduleRequest) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        // Validate ownership
        if (!schedule.getProvider().getId().equals(providerId)) {
            throw new RuntimeException("Schedule does not belong to this provider");
        }

        schedule.setDayOfWeek(scheduleRequest.getDayOfWeek());
        schedule.setAvailableFrom(scheduleRequest.getAvailableFrom());
        schedule.setAvailableTo(schedule.getAvailableTo());

        Schedule updated = scheduleRepository.save(schedule);
        return toResponseDTO(updated);
    }
    @Override
    public void deleteSchedule(Long providerId, Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        if (!schedule.getProvider().getId().equals(providerId)) {
            throw new RuntimeException("Schedule does not belong to this provider");
        }

        scheduleRepository.delete(schedule);
    }
    private ScheduleResponse toResponseDTO(Schedule schedule) {
        ScheduleResponse dto = new ScheduleResponse();
        dto.setId(schedule.getId());
        dto.setProviderId(schedule.getProvider().getId());
        dto.setDayOfWeek(schedule.getDayOfWeek());
        dto.setAvailableFrom(schedule.getAvailableFrom());
        dto.setAvailableTo(schedule.getAvailableTo());
        return dto;
    }
    private Schedule toEntity(ScheduleRequest dto, Provider provider) {
        return Schedule.builder()
                .provider(provider)
                .dayOfWeek(dto.getDayOfWeek())
                .availableFrom(dto.getAvailableFrom())
                .availableTo(dto.getAvailableTo())
                .build();
    }

}