package com.example.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.application.entities.Food;

public interface FoodRepository extends JpaRepository<Food, Long>{

}
