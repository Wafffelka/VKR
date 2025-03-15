package com.example.weatherclothing.controller;

import ch.qos.logback.classic.Logger;
import com.example.weatherclothing.model.ClothingItem;
import com.example.weatherclothing.repository.ClothingRepository;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clothing")
public class ClothingController {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(ClothingController.class);
    private final ClothingRepository clothingRepository;

    public ClothingController(ClothingRepository clothingRepository) {
        this.clothingRepository = clothingRepository;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addClothingItem(@RequestBody ClothingItem item) {
        logger.info("Добавление одежды: {}", item);
        clothingRepository.save(item);
        return ResponseEntity.ok("Одежда добавлена!");
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ClothingItem>> getUserClothing(@PathVariable String userId) {
        logger.info("Получение одежды для пользователя: {}", userId);
        return ResponseEntity.ok(clothingRepository.findByUserId(userId));
    }
}