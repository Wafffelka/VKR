package com.example.weatherclothing.controller;

import com.example.weatherclothing.model.ClothingItem;
import com.example.weatherclothing.repository.ClothingRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clothing")
public class ClothingController {

    private final ClothingRepository clothingRepository;

    public ClothingController(ClothingRepository clothingRepository) {
        this.clothingRepository = clothingRepository;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addClothingItem(@RequestBody ClothingItem item) {
        clothingRepository.save(item);
        return ResponseEntity.ok("Одежда добавлена!");
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ClothingItem>> getUserClothing(@PathVariable String userId) {
        return ResponseEntity.ok(clothingRepository.findByUserId(userId));
    }
}