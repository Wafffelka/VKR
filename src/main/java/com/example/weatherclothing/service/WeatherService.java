package com.example.weatherclothing.service;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@Service
public class WeatherService {
    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

    @Value("${openweathermap.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;
    private final StringRedisTemplate redisTemplate;

    public WeatherService(RestTemplate restTemplate, StringRedisTemplate redisTemplate) {
        this.restTemplate = restTemplate;
        this.redisTemplate = redisTemplate;
    }

    @Cacheable(value = "weather", key = "#city")
    public String getWeatherData(String city) {
        logger.info("Запрос погодных данных для города: {}", city);

        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String cachedData = ops.get(city);

        if (cachedData != null) {
            logger.info("Используются кэшированные данные для города: {}", city);
            return cachedData;
        }

        String url = String.format("https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric", city, apiKey);
        String response = restTemplate.getForObject(url, String.class);

        logger.info("Получены данные с OpenWeatherMap: {}", response);

        ops.set(city, response, 10, TimeUnit.MINUTES);
        return response;
    }
}
