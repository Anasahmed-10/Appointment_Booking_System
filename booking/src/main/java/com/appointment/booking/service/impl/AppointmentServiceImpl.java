package com.appointment.booking.service.impl;

import com.appointment.booking.controller.dto.AppointmentRequest;
import com.appointment.booking.controller.dto.AppointmentResponse;
import com.appointment.booking.model.AppointmentStatus;
import com.appointment.booking.model.DaysOfWeek;
import com.appointment.booking.model.*;
import com.appointment.booking.repository.*;
import com.appointment.booking.service.AppointmentService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private ProviderRepository providerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ServiceEntityRepository serviceRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;

    // -----------------------------------
    // CREATE Appointment
    // -----------------------------------
    @Override
    public AppointmentResponse createAppointment(AppointmentRequest dto, User currentUser) {

        if (!currentUser.getRole().equals(UserRole.CUSTOMER)) {
            throw new RuntimeException("Only customers can book appointments");
        }
        if (!dto.getUserId().equals(currentUser.getId())) {
            throw new RuntimeException("Cannot book appointment for another user");
        }
        User user = currentUser;
        Provider provider = providerRepository.findById(dto.getProviderId())
                .orElseThrow(() -> new RuntimeException("Provider not found"));

        ServiceEntity serviceEntity = serviceRepository.findById(dto.getServiceId())
                .orElseThrow(() -> new RuntimeException("Service not found"));

        validateTimeRange(dto.getStartTime(), dto.getEndTime());
        validateProviderSchedule(provider, dto);
        validateNoOverlaps(provider, dto);

        Appointment appointment = Appointment.builder()
                .user(user)
                .provider(provider)
                .serviceEntity(serviceEntity)
                .appointmentDate(dto.getDate())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .status(AppointmentStatus.PENDING)
                .build();

        Appointment saved = appointmentRepository.save(appointment);
        return toResponseDTO(saved);
    }

    // GET appointments by provider
    @Override
    public List<AppointmentResponse> getAppointmentsByProvider(Long providerId, User currentUser) {
        Provider provider = providerRepository.findById(providerId)
                .orElseThrow(() -> new RuntimeException("Provider not found"));

        if (!currentUser.getRole().equals(UserRole.ADMIN) &&
                !provider.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Access denied");
        }
        
        return appointmentRepository.findByProviderId(providerId)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // GET appointments by user
    @Override
    public List<AppointmentResponse> getAppointmentsByUser(Long userId, User currentUser) {
        if (!currentUser.getRole().equals(UserRole.ADMIN) && !userId.equals(currentUser.getId())) {
            throw new RuntimeException("Access denied");
        }   
        
        return appointmentRepository.findByUserId(userId)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // UPDATE status (Confirm / Cancel / Complete)
    @Override
    public AppointmentResponse updateAppointmentStatus(Long appointmentId, String status, User currentUser) {

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        if (!currentUser.getRole().equals(UserRole.ADMIN) &&
                !appointment.getProvider().getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Access denied");
        }           
        AppointmentStatus newStatus = AppointmentStatus.valueOf(status.toUpperCase());
        appointment.setStatus(newStatus);

        Appointment updated = appointmentRepository.save(appointment);
        return toResponseDTO(updated);
    }

    // DELETE
    @Override
    public void deleteAppointment(Long appointmentId, User currentUser) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        boolean isOwner = appointment.getUser().getId().equals(currentUser.getId());
        boolean isProvider = appointment.getProvider().getUser().getId().equals(currentUser.getId());
        boolean isAdmin = currentUser.getRole().equals(UserRole.ADMIN);
        
        if (!isOwner && !isProvider && !isAdmin) {
            throw new RuntimeException("Access denied");
        }
        appointmentRepository.delete(appointment);
    }

    // VALIDATION METHODS

    private void validateTimeRange(LocalTime start, LocalTime end) {
        if (!end.isAfter(start)) {
            throw new RuntimeException("End time must be after start time");
        }
    }

    private void validateProviderSchedule(Provider provider, AppointmentRequest dto) {
        DaysOfWeek day = DaysOfWeek.valueOf(dto.getDate().getDayOfWeek().name());

        List<Schedule> schedules = scheduleRepository.findByProviderId(provider.getId());

        Schedule matched = schedules.stream()
                .filter(s -> s.getDayOfWeek().equals(day))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Provider is not available on this day"));

        if (dto.getStartTime().isBefore(matched.getAvailableFrom()) ||
                dto.getEndTime().isAfter(matched.getAvailableTo())) {

            throw new RuntimeException("Appointment time is outside provider availability");
        }
    }

    private void validateNoOverlaps(Provider provider, AppointmentRequest dto) {

        List<Appointment> existing = appointmentRepository
                .findByProviderIdAndAppointmentDate(provider.getId(), dto.getDate());

        for (Appointment ap : existing) {
            boolean overlaps =
                    dto.getStartTime().isBefore(ap.getEndTime()) &&     //This means that there already exists an appointment
                            dto.getEndTime().isAfter(ap.getStartTime());

            if (overlaps) {
                throw new RuntimeException("Appointment overlaps with an existing booking");
            }
        }
    }

    //Mapping Method

    private AppointmentResponse toResponseDTO(Appointment ap) {
        AppointmentResponse dto = new AppointmentResponse();
        dto.setId(ap.getId());
        dto.setUserId(ap.getUser().getId());
        dto.setProviderId(ap.getProvider().getId());
        dto.setServiceId(ap.getServiceEntity().getId());
        dto.setDate(ap.getAppointmentDate());
        dto.setStartTime(ap.getStartTime());
        dto.setEndTime(ap.getEndTime());
        dto.setStatus(ap.getStatus().name());
        dto.setCreatedAt(ap.getCreatedAt());
        return dto;
    }
}