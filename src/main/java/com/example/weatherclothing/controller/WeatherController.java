package com.example.weatherclothing.controller;

import com.example.weatherclothing.service.RecommendationService;
import com.example.weatherclothing.service.WeatherService;
import com.example.weatherclothing.model.UserPreference;
import com.example.weatherclothing.repository.UserPreferenceRepository;
import com.example.weatherclothing.repository.ClothingRepository;
import com.example.weatherclothing.model.ClothingItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(WeatherController.class);

    public WeatherController(WeatherService weatherService, RecommendationService recommendationService,
                             UserPreferenceRepository userPreferenceRepository, ClothingRepository clothingRepository) {
        this.weatherService = weatherService;
        this.recommendationService = recommendationService;
        this.userPreferenceRepository = userPreferenceRepository;
        this.clothingRepository = clothingRepository;
    }

    @GetMapping("/recommendations")
    public ResponseEntity<String> getRecommendations(@RequestParam String userId, @RequestParam String city, @RequestParam String activity) {
        logger.info("Получение рекомендаций для пользователя: {}, город: {}, активность: {}", userId, city, activity);
        try {
            String weatherData = weatherService.getWeatherData(city);
            UserPreference preference = userPreferenceRepository.findByUserId(userId);
            if (preference == null) {
                logger.warn("Не найдены предпочтения пользователя: userId={}", userId);
                return ResponseEntity.badRequest().body("Предпочтения пользователя не найдены");
            }

            String recommendation = recommendationService.generateRecommendation(weatherData, activity, preference, userId);

            List<ClothingItem> userClothing = clothingRepository.findByUserId(userId);
            Set<String> availableColors = userClothing.stream().map(ClothingItem::getColor).collect(Collectors.toSet());

            recommendation += "\nКакой цвет вы предпочитаете сегодня? Доступные цвета: " + availableColors;
            logger.info("Рекомендация успешно сгенерирована для пользователя: {}", userId);
            return ResponseEntity.ok(recommendation);
        } catch (Exception e) {
            logger.error("Ошибка при получении рекомендаций для пользователя {}: {}", userId, e.getMessage(), e);
            return ResponseEntity.status(500).body("Ошибка при генерации рекомендаций");
        }
    }
}

