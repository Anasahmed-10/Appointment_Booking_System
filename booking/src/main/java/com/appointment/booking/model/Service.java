package com.appointment.booking.model;



import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "services")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false)
    private String name; //(Haircut, Consultation)

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private int duration;

    @Column(nullable = false)
    private double price;

    @ManyToOne
    @JoinColumn(name="provider_id", nullable = false)
    private Provider provider;
}

