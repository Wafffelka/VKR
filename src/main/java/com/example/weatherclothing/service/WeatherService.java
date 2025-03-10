package com.example.weatherclothing.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class WeatherService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final RestTemplate restTemplate;
    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

    public WeatherService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.restTemplate = new RestTemplate();
    }

    public String getWeatherData(String city) {
        logger.info("Запрос данных о погоде для города: {}", city);

        String cacheKey = "weather:" + city;

        // Проверяем кэш Redis
        if (Boolean.TRUE.equals(redisTemplate.hasKey(cacheKey))) {
            return (String) redisTemplate.opsForValue().get(cacheKey);
        }

        try {
            // Делаем запрос к API
            String apiKey = "e318e730de6e7efe9ba52e450398d45b";
            String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey + "&units=metric";
            String response = restTemplate.getForObject(url, String.class);

            logger.info("Получены новые данные о погоде.");

            // Сохраняем ответ в кэш на 10 минут
            redisTemplate.opsForValue().set(cacheKey, response, Duration.ofMinutes(10));

            logger.info("Данные сохранены.");

            return response;



        } catch (Exception e) {
            logger.error("Ошибка при получении погоды для {}: {}", city, e.getMessage(), e);
            throw new RuntimeException("Не удалось получить данные о погоде");
        }
    }

}
