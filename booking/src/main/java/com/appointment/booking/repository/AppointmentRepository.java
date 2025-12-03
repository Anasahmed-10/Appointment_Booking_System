package com.appointment.booking.repository;

import com.appointment.booking.model.Appointment;
import com.appointment.booking.model.Provider;
import com.appointment.booking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByUserId(Long userId);

    List<Appointment> findByProviderId(Long providerId);

    List<Appointment> findByProviderIdAndAppointmentDate(Long providerId, LocalDate date);
}