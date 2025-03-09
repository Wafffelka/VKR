package com.example.weatherclothing.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;

@Entity
@Getter
@Setter
public class ClothingItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private String type; // Тип одежды
    private double temperature;
    private String color;

    @ElementCollection
    private Set<String> tags; // Дополнительные теги

}

