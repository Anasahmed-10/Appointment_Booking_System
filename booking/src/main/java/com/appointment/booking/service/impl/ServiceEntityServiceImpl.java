package com.appointment.booking.service.impl;

import com.appointment.booking.model.ServiceEntity;
import com.appointment.booking.model.Provider;
import com.appointment.booking.repository.ServiceEntityRepository;
import com.appointment.booking.service.ServiceEntityService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceEntityServiceImpl implements ServiceEntityService {

    private final ServiceEntityRepository serviceEntityRepository;

    @Override
    public ServiceEntity createService(ServiceEntity serviceEntity) {
        return serviceEntityRepository.save(serviceEntity);
    }

    @Override
    public List<ServiceEntity> findByProvider(Provider provider) {
        return serviceEntityRepository.findByProvider(provider);
    }

    @Override
    public Optional<ServiceEntity> findById(Long id) {
        return serviceEntityRepository.findById(id);
    }
}