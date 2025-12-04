package com.appointment.booking.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceRequest {

    @NotBlank(message = "Service name is required")
    private String name;

    private String description;

    @NotNull(message = "Duration in minutes is required")
    private Integer duration;  // in minutes

    @NotNull(message = "Price is required")
    private Double price;

    @NotNull(message = "Provider ID is required")
    private Long providerId;
}