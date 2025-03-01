package com.example.weatherclothing.repository;

import com.example.weatherclothing.model.ClothingItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ClothingRepository extends JpaRepository<ClothingItem, Long> {
    List<ClothingItem> findByUserId(String userId);
}
