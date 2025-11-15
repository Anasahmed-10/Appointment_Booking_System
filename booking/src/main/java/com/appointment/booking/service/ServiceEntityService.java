package com.appointment.booking.service;

import com.appointment.booking.model.ServiceEntity;
import com.appointment.booking.model.Provider;

import java.util.List;
import java.util.Optional;

public interface ServiceEntityService { //for services offered by Provider

    ServiceEntity createService(ServiceEntity serviceEntity);

    List<ServiceEntity> findByProvider(Provider provider);

    Optional<ServiceEntity> findById(Long id);
}
