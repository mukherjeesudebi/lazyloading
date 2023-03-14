package com.example.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.application.entities.Occupation;

public interface OccupationRepository extends JpaRepository<Occupation, Long>{

}
