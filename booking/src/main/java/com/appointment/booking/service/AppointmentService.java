package com.appointment.booking.service;

import com.appointment.booking.model.*;
import com.appointment.booking.controller.dto.AppointmentRequest;
import com.appointment.booking.controller.dto.AppointmentResponse;
import java.time.LocalDate;
import java.util.List;

public interface AppointmentService {

    AppointmentResponse createAppointment(AppointmentRequest dto);

    List<AppointmentResponse> getAppointmentsByProvider(Long providerId);

    List<AppointmentResponse> getAppointmentsByUser(Long userId);

    AppointmentResponse updateAppointmentStatus(Long appointmentId, String status);

    void deleteAppointment(Long appointmentId);

}
