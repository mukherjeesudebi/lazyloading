package com.example.application.repositories;

import com.example.application.entities.Food;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long>{
	List<Food> findAllByName(String name, Pageable pageable);
	List<Food> findAllByNameContainingIgnoreCase(String name, Pageable pageable);
}
