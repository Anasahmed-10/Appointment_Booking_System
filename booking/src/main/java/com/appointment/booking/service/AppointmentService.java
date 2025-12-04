package com.appointment.booking.service;

import com.appointment.booking.model.User;
import com.appointment.booking.controller.dto.AppointmentRequest;
import com.appointment.booking.controller.dto.AppointmentResponse;
import java.util.List;

public interface AppointmentService {

    AppointmentResponse createAppointment(AppointmentRequest dto, User currentUser);

    List<AppointmentResponse> getAppointmentsByProvider(Long providerId, User currentUser);

    List<AppointmentResponse> getAppointmentsByUser(Long userId, User currenUser);

    AppointmentResponse updateAppointmentStatus(Long appointmentId, String status, User currentUser);

    void deleteAppointment(Long appointmentId, User currentUser);

}
