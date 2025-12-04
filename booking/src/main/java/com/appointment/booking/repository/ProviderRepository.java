package com.appointment.booking.repository;

import com.appointment.booking.model.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import com.appointment.booking.model.User;
import java.util.Optional;

public interface ProviderRepository extends JpaRepository<Provider, Long> {
    // Custom queries can be added later if needed
    Optional<Provider> findByUser(User user);
}
