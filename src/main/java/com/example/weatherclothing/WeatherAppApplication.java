package com.example.weatherclothing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class WeatherAppApplication {
    private static final Logger logger = LoggerFactory.getLogger(WeatherAppApplication.class);
    public static void main(String[] args) {
        logger.info("Запуск WxStyle"); //Название проекта
        SpringApplication.run(WeatherAppApplication.class, args);
    }
}

