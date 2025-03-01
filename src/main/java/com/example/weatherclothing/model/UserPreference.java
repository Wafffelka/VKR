package com.example.weatherclothing.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserPreference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private String city;
    private String activity;
    private Double preferredTemperature;
    private Boolean prefersRain;
    private Double preferredWindSpeed;
}
