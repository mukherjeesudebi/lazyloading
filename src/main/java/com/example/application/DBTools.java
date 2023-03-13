package com.example.application;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.application.entities.Person;
import com.example.application.repositories.PersonRepository;

@Component
public class DBTools {

	private static final Logger log = LoggerFactory.getLogger(DBTools.class);

	@Autowired
	PersonRepository personRepository;

	public void clear() {
		log.info("Removing all persons... ");
		personRepository.deleteAll();
	}

	public Person createPerson(String firstName, String lastName, String email, String profession) {
		Person person = new Person();
		person.setFirstName(firstName);
		person.setLastName(lastName);
		person.setEmail(email);
		person.setProfession(profession);
		return personRepository.save(person);
	}

	public void create() {
		log.info("======== CREATING DATABASE ======== ");
		for (int i = 0; i < 2000; i++) {
			Random random = new Random();
			String firstName = "Person" + i;
			String lastName = "Surname" + i;
			String email = firstName.concat(".").concat(lastName).concat("@email.com");
			String profession = getProfession(random.nextInt(4));

			log.info("======== creating Person ======== ");
			createPerson(firstName, lastName, email, profession);
		}

		log.info("Done.");
	}

	public String getProfession(int random) {
		if (random == 0) {
			return "Teacher";
		} else if (random == 1) {
			return "Doctor";
		} else if (random == 2) {
			return "Engineer";
		} else if (random == 3) {
			return "Service";
		} else {
			return "Professor";
		}
	}

}
