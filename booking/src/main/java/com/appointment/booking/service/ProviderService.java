package com.appointment.booking.service;

import com.appointment.booking.controller.dto.ProviderRequest;
import com.appointment.booking.model.Provider;
import com.appointment.booking.model.User;
import java.util.List;
import java.util.Optional;

public interface ProviderService {

    Provider createProvider(Provider provider);

    public Optional<Provider> findByUser(User user);

    Provider updateProvider(Long id, ProviderRequest req, User currentUser);

    void deleteProvider(Long id, User currentUser);

    Optional<Provider> findById(Long id);

    List<Provider> findAll();
}
