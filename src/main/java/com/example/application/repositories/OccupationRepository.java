package com.example.application.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.application.entities.Occupation;

public interface OccupationRepository extends JpaRepository<Occupation, Long>{
	List<Occupation> findAllByName(String name, Pageable pageable);
	List<Occupation> findAllByNameContainingIgnoreCase(String name, Pageable pageable);
}
