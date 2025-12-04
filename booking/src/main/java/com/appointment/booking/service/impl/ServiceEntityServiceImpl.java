package com.appointment.booking.service.impl;

import com.appointment.booking.model.ServiceEntity;
import com.appointment.booking.model.Provider;
import com.appointment.booking.repository.ServiceEntityRepository;
import com.appointment.booking.service.ServiceEntityService;
import com.appointment.booking.controller.dto.ServiceRequest;
import com.appointment.booking.service.ProviderService;
import lombok.RequiredArgsConstructor;
import com.appointment.booking.model.User;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceEntityServiceImpl implements ServiceEntityService {

    @Autowired
    private final ServiceEntityRepository serviceEntityRepository;
    @Autowired
    private final ProviderService providerService;

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

    @Override
    public ServiceEntity updateService(Long id, ServiceRequest req, User currentUser) {
        ServiceEntity service = serviceEntityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found"));
        
        // Optional: update provider if needed
        if (!service.getProvider().getUser().getId().equals(currentUser.getId())
            && currentUser.getRole() != com.appointment.booking.model.UserRole.ADMIN) {
        throw new RuntimeException("Unauthorized to update this service");
    }
        if (req.getProviderId() != null) {
            Provider provider = providerService.findById(req.getProviderId())
                    .orElseThrow(() -> new RuntimeException("Provider not found"));
            service.setProvider(provider);
        }

        service.setName(req.getName());
        service.setDescription(req.getDescription());
        service.setDuration(req.getDuration());
        service.setPrice(req.getPrice());

        return serviceEntityRepository.save(service);
    }

    @Override
    public void deleteService(Long id, User currentUser) {
        ServiceEntity service = serviceEntityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        if (!service.getProvider().getUser().getId().equals(currentUser.getId())
            && currentUser.getRole() != com.appointment.booking.model.UserRole.ADMIN) {
        throw new RuntimeException("Unauthorized to delete this service");
        }
        serviceEntityRepository.delete(service);
    }

}