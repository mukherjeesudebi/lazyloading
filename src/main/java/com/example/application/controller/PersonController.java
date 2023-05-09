package com.example.application.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.application.dto.PersonDTO;
import com.example.application.repositories.PersonRepository;
import com.example.application.service.PersonDTOConverter;
import com.example.application.service.PersonService;

@RestController
public class PersonController {
	

	private static final Logger log = LoggerFactory.getLogger(PersonService.class);

    private final PersonRepository personRepository;
    private final PersonDTOConverter personDTOConverter;
    
    public PersonController(@Autowired PersonRepository personRepository, @Autowired PersonDTOConverter personDTOConverter) {
        this.personRepository = personRepository;
        this.personDTOConverter = personDTOConverter;
    }

	@GetMapping("/list")
	public List<PersonDTO> list(int pageSize, int page) {
		return personRepository.findAll(Pageable.ofSize(pageSize).withPage(page)).stream().map(personDTOConverter::convertToDTO).toList();
	}
}
