package com.example.weatherclothing.service;

import com.example.weatherclothing.model.ClothingItem;
import com.example.weatherclothing.model.UserPreference;
import com.example.weatherclothing.repository.ClothingRepository;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    private final ClothingRepository clothingRepository;

    public RecommendationService(ClothingRepository clothingRepository) {
        this.clothingRepository = clothingRepository;
    }

    public String generateRecommendation(String weatherData, String activity, UserPreference preference, String userId) {
        JSONObject json = new JSONObject(weatherData);
        double temperature = json.getJSONObject("main").getDouble("temp");
        double windSpeed = json.getJSONObject("wind").getDouble("speed");
        boolean isRaining = json.getJSONArray("weather").getJSONObject(0).getString("main").equalsIgnoreCase("Rain");

        // Фильтруем гардероб пользователя по температуре
        List<ClothingItem> suitableClothes = clothingRepository.findByUserId(userId).stream()
                .filter(item -> Math.abs(item.getTemperature() - temperature) <= 5)
                .collect(Collectors.toList());

        StringBuilder recommendation = new StringBuilder("Рекомендация: ");
        if (temperature < 0) recommendation.append("Теплая куртка, шарф, шапка, перчатки. ");
        else if (temperature < 10) recommendation.append("Легкая куртка, свитер. ");
        else if (temperature < 20) recommendation.append("Легкая кофта или куртка. ");
        else recommendation.append("Футболка и легкая одежда. ");

        if (isRaining) recommendation.append(" Не забудьте взять зонт. ");
        if (windSpeed > 10) recommendation.append(" Наденьте защиту от ветра. ");

        recommendation.append("\nВаши вещи, подходящие по температуре:\n");
        for (ClothingItem item : suitableClothes) {
            recommendation.append("- ").append(item.getType()).append(" (").append(item.getColor()).append(")\n");
        }

        return recommendation.toString();
    }
}
