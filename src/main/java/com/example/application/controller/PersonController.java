package com.example.application.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.application.dto.PersonDTO;
import com.example.application.dto.PersonRestClientDTO;
import com.example.application.entities.Person;
import com.example.application.repositories.PersonRepository;
import com.example.application.service.PersonDTOConverter;

@RestController
public class PersonController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PersonController.class);

	private final PersonRepository personRepository;
	private final PersonDTOConverter personDTOConverter;

	public PersonController(@Autowired PersonRepository personRepository,
			@Autowired PersonDTOConverter personDTOConverter) {
		this.personRepository = personRepository;
		this.personDTOConverter = personDTOConverter;
	}

	@PostMapping("/list")
	public List<PersonDTO> list(@RequestBody PersonRestClientDTO data) {
		return personRepository.findAll(data.toPageable()).stream().map(personDTOConverter::convertToDTO).toList();
	}

	@PostMapping(value = "/findAllByFilter", consumes = { "application/json" })
	public List<PersonDTO> findAllByFilter(@RequestBody PersonRestClientDTO data) {
		return personRepository.findAllByFilter(data.toPageable(),data.getPersonFilter(),data.getSortKey(),data.getDirection()).stream().map(personDTOConverter::convertToDTO).toList();
	}
	
	@PostMapping(value = "/findById")
	public Optional<PersonDTO> findById(@RequestBody Long personId) {
		return personRepository.findById(personId).map(personDTOConverter::convertToDTO);
	}
	
	@PostMapping(value = "/save")
	public PersonDTO save(@RequestBody PersonDTO dto) {
		return personDTOConverter
				.convertToDTO(personRepository.saveAndFlush(personDTOConverter.convertToEntity(this::findEntity, dto)));
	}
	
	private Person findEntity(PersonDTO item) {
		if (item.getId() == null) {
			return new Person();
		}
		Optional<Person> existingEntity = personRepository.findById(item.getId());
		if (existingEntity.isEmpty()) {
			throw new RuntimeException("Attempt to modify an entity that does not exist");
		}
		return existingEntity.get();
	}
}


