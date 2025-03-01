package com.example.weatherclothing.controller;

import com.example.weatherclothing.service.RecommendationService;
import com.example.weatherclothing.service.WeatherService;
import com.example.weatherclothing.model.UserPreference;
import com.example.weatherclothing.repository.UserPreferenceRepository;
import com.example.weatherclothing.repository.ClothingRepository;
import com.example.weatherclothing.model.ClothingItem;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/weather")
public class WeatherController {
    private final WeatherService weatherService;
    private final RecommendationService recommendationService;
    private final UserPreferenceRepository userPreferenceRepository;
    private final ClothingRepository clothingRepository;

    public WeatherController(WeatherService weatherService, RecommendationService recommendationService,
                             UserPreferenceRepository userPreferenceRepository, ClothingRepository clothingRepository) {
        this.weatherService = weatherService;
        this.recommendationService = recommendationService;
        this.userPreferenceRepository = userPreferenceRepository;
        this.clothingRepository = clothingRepository;
    }

    @GetMapping("/recommendations")
    public ResponseEntity<String> getRecommendations(@RequestParam String userId, @RequestParam String city, @RequestParam String activity) {
        String weatherData = weatherService.getWeatherData(city);
        UserPreference preference = userPreferenceRepository.findByUserId(userId);
        String recommendation = recommendationService.generateRecommendation(weatherData, activity, preference, userId);

        List<ClothingItem> userClothing = clothingRepository.findByUserId(userId);
        Set<String> availableColors = userClothing.stream().map(ClothingItem::getColor).collect(Collectors.toSet());

        recommendation += "\nКакой цвет вы предпочитаете сегодня? Доступные цвета: " + availableColors;

        return ResponseEntity.ok(recommendation);
    }
}

