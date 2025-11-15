package com.appointment.booking.repository;

import com.appointment.booking.model.ServiceEntity;
import com.appointment.booking.model.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ServiceEntityRepository extends JpaRepository<ServiceEntity, Long> {
    List<ServiceEntity> findByProvider(Provider provider); //find all services offered by a provider
}