package com.appointment.booking.service.impl;

import com.appointment.booking.model.Provider;
import com.appointment.booking.repository.ProviderRepository;
import com.appointment.booking.service.ProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProviderServiceImpl implements ProviderService {

    private final ProviderRepository providerRepository;

    @Override
    public Provider createProvider(Provider provider) {
        return providerRepository.save(provider);
    }

    @Override
    public Optional<Provider> findById(Long id) {
        return providerRepository.findById(id);
    }

    @Override
    public List<Provider> findAll() {
        return providerRepository.findAll();
    }
}
