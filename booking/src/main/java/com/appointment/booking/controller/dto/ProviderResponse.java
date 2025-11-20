package com.appointment.booking.controller.dto;

import com.appointment.booking.model.Provider;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProviderResponse {

    private Long id;
    private Long userId;
    private String specialization;
    private String description;
    private String contactInfo;

    public static ProviderResponse fromEntity(Provider provider) {
        return new ProviderResponse(
                provider.getId(),
                provider.getUser().getId(),
                provider.getSpecialization(),
                provider.getDescription(),
                provider.getContactInfo()
        );
    }
}