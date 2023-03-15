package com.example.application.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.application.entities.Person;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long>{

    List<Person> findAllByEmail(String email, Pageable pageable);

}
