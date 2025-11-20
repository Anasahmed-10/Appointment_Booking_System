package com.appointment.booking.service;

import com.appointment.booking.controller.dto.ProviderRequest;
import com.appointment.booking.model.Provider;
import java.util.List;
import java.util.Optional;

public interface ProviderService {

    Provider createProvider(Provider provider);

    Provider updateProvider(Long id, ProviderRequest req);

    void deleteProvider(Long id);

    Optional<Provider> findById(Long id);

    List<Provider> findAll();
}
