package com.example.application.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.application.entities.Food;
import com.example.application.entities.Occupation;

public interface FoodRepository extends JpaRepository<Food, Long>{
	List<Food> findAllByName(String name, Pageable pageable);
	List<Food> findAllByNameContainingIgnoreCase(String name, Pageable pageable);
}
