package com.appointment.booking.service.impl;

import com.appointment.booking.controller.dto.ProviderRequest;
import com.appointment.booking.model.Provider;
import com.appointment.booking.model.User;
import com.appointment.booking.repository.ProviderRepository;
import com.appointment.booking.service.ProviderService;
import com.appointment.booking.service.UserService;
import lombok.RequiredArgsConstructor;
import com.appointment.booking.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProviderServiceImpl implements ProviderService {

    @Autowired
    private ProviderRepository providerRepository;
 
    private final UserService userService;

    @Override
    public Provider createProvider(Provider provider) {
        return providerRepository.save(provider);
    }

    @Override
    public Optional<Provider> findById(Long id) {
        Provider provider = providerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Provider not found"));
        return providerRepository.findById(id);
    }
    public Optional<Provider> findByUser(User user) {
        return providerRepository.findByUser(user);
    }

    @Override
    public Provider updateProvider(Long id, ProviderRequest req, User currentUser) {
        Provider provider = providerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Provider not found"));
        if (!provider.getUser().getId().equals(currentUser.getId()) 
            && currentUser.getRole() != UserRole.ADMIN) {
        throw new RuntimeException("Not authorized");
        }

        // Optional: update linked user if userId provided
        if (req.getUserId() != null) {
            User user = userService.findById(req.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            provider.setUser(user);
        }

        provider.setSpecialization(req.getSpecialization());
        provider.setDescription(req.getDescription());
        provider.setContactInfo(req.getContactInfo());

        return providerRepository.save(provider);
    }
    @Override
    public void deleteProvider(Long id, User currentUser) {
        Provider provider = providerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Provider not found"));
        if (!provider.getUser().getId().equals(currentUser.getId()) 
            && currentUser.getRole() != UserRole.ADMIN) {
         throw new RuntimeException("You are not authorized to delete this provider");
            }
        providerRepository.delete(provider);
    }

    @Override
    public List<Provider> findAll() {
        return providerRepository.findAll();
    }
}
