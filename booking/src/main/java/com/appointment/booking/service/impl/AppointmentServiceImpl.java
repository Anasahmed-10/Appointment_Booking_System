package com.appointment.booking.service.impl;

import com.appointment.booking.model.*;
import com.appointment.booking.repository.AppointmentRepository;
import com.appointment.booking.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    @Override
    public Appointment bookAppointment(Appointment appointment) {
        // Advanced validation logic will be added later
        return appointmentRepository.save(appointment);
    }

    @Override
    public List<Appointment> findByCustomer(User customer) {
        return appointmentRepository.findByCustomer(customer);
    }

    @Override
    public List<Appointment> findByProvider(Provider provider) {
        return appointmentRepository.findByProvider(provider);
    }

    @Override
    public List<Appointment> findByProviderAndDate(Provider provider, LocalDate date) {
        return appointmentRepository.findByProviderAndAppointmentDate(provider, date);
    }
}
