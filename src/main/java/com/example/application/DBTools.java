package com.example.application;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.application.entities.Food;
import com.example.application.entities.Occupation;
import com.example.application.entities.Person;
import com.example.application.repositories.FoodRepository;
import com.example.application.repositories.OccupationRepository;
import com.example.application.repositories.PersonRepository;

@Component
public class DBTools {

	private static final Logger log = LoggerFactory.getLogger(DBTools.class);

	@Autowired
	PersonRepository personRepository;

	@Autowired
	OccupationRepository occupationRepository;

	@Autowired
	FoodRepository foodRepository;

	public void clear() {
		log.info("Removing all persons... ");
		personRepository.deleteAll();

		log.info("Removing all occupations... ");
		occupationRepository.deleteAll();

		log.info("Removing all food... ");
		foodRepository.deleteAll();
	}

	public Person createPerson(String firstName, String lastName, String email) {
		Person person = new Person();
		person.setFirstName(firstName);
		person.setLastName(lastName);
		person.setEmail(email);
		return personRepository.save(person);
	}

	public Occupation createOccupation(String name) {
		Occupation occupation = new Occupation();
		occupation.setName(name);
		return occupation;
	}

	public Food createFood(String name) {
		Food food = new Food();
		food.setName(name);
		return food;
	}

	public void create() {
		log.info("======== CREATING DATABASE ======== ");
		for (int i = 0; i < 2000; i++) {
			String firstName = "Person" + i;
			String lastName = "Surname" + i;
			String email = firstName.concat(".").concat(lastName).concat("@email.com");
			log.info("======== creating Person ======== ");
			createPerson(firstName, lastName, email);
		}

		for (int i = 0; i < 100; i++) {
			String occupationName = "Occupation" + i;
			log.info("======== creating Occupation ======== ");
			createOccupation(occupationName);
		}
		
		for (int i = 0; i < 100; i++) {
			String foodName = "Food" + i;
			log.info("======== creating food ======== ");
			createFood(foodName);
		}

		log.info("Done.");
	}

}
