package com.appointment.booking.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "provider")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Provider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String specialization;

    @Column(length = 500)
    private String description;

    @Column(length = 20)
    private String contactInfo;


    @OneToMany(mappedBy = "provider", cascade = CascadeType.ALL)
    private List<ServiceEntity> serviceEntity; //One provider can offer multiple Services

    @OneToMany(mappedBy = "provider", cascade = CascadeType.ALL)
    private List<Schedule> schedules; //One provider can have multiple schedules

    @OneToMany(mappedBy = "provider", cascade = CascadeType.ALL)
    private List<Appointment> appointments; //One provider can have multiple appointments
}
