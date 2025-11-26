package com.appointment.booking.repository;

import com.appointment.booking.model.Schedule;
import com.appointment.booking.model.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByProvider(Provider provider);

    List<Schedule> findByProvideId(Long providerId);
}