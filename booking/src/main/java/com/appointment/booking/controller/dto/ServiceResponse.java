package com.appointment.booking.controller.dto;

import com.appointment.booking.model.ServiceEntity;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceResponse {

    private Long id;
    private String name;
    private String description;
    private Integer duration; // in minutes
    private Double price;
    private Long providerId;

    public static ServiceResponse fromEntity(ServiceEntity service) {
        return new ServiceResponse(
                service.getId(),
                service.getName(),
                service.getDescription(),
                service.getDuration(),
                service.getPrice(),
                service.getProvider().getId()
        );
    }
}
