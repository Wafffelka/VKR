package com.example.weatherclothing;

import com.example.weatherclothing.config.SecurityConfig;
import com.example.weatherclothing.controller.WeatherController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WeatherAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(WeatherAppApplication.class, args);
    }
}

