package com.example.application;

import com.example.application.entities.Food;
import com.example.application.entities.Occupation;
import com.example.application.entities.Person;
import com.example.application.repositories.FoodRepository;
import com.example.application.repositories.OccupationRepository;
import com.example.application.repositories.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DBTools {

	private static final Logger log = LoggerFactory.getLogger(DBTools.class);

	final PersonRepository personRepository;

	final OccupationRepository occupationRepository;

	final FoodRepository foodRepository;

	public DBTools(@Autowired PersonRepository personRepository,
				   @Autowired OccupationRepository occupationRepository,
                   @Autowired FoodRepository foodRepository) {
		this.personRepository = personRepository;
		this.occupationRepository = occupationRepository;
		this.foodRepository = foodRepository;
	}

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
		return occupationRepository.save(occupation);
	}

	public Food createFood(String name) {
		Food food = new Food();
		food.setName(name);
		return foodRepository.save(food);
	}

	public void create() {
		log.info("======== CREATING DATABASE ======== ");
		for (int i = 1; i <= 5000; i++) {
			String firstName = "Person" + i;
			String lastName = "Surname" + i;
			String email = firstName.concat(".").concat(lastName).concat("@email.com");
			log.info("======== creating Person ======== ");
			createPerson(firstName, lastName, email);
		}

		for (int i = 1; i <= 500; i++) {
			String occupationName = "Occupation" + i;
			log.info("======== creating Occupation ======== ");
			createOccupation(occupationName);
		}

		for (int i = 1; i <= 500; i++) {
			String foodName = "Food" + i;
			log.info("======== creating food ======== ");
			createFood(foodName);
		}

		log.info("Done.");
	}

}
