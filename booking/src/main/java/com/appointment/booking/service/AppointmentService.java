package com.appointment.booking.service;

import com.appointment.booking.model.*;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentService {

    Appointment bookAppointment(Appointment appointment);

    List<Appointment> findByCustomer(User customer);

    List<Appointment> findByProvider(Provider provider);

    List<Appointment> findByProviderAndDate(Provider provider, LocalDate date);
}