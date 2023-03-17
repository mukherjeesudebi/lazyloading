package com.example.application.service;

import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.application.dto.PersonDTO;
import com.example.application.dto.PersonFilterDTO;
import com.example.application.entities.Person;
import com.example.application.repositories.PersonRepository;
import com.vaadin.flow.data.provider.Query;

@Service
public class PersonService implements DataService<PersonDTO, PersonFilterDTO> {
	
	private static final Logger log = LoggerFactory.getLogger(PersonService.class);

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonDTOConverter personDTOConverter;

    @Override
    public Stream<PersonDTO> list(Query<PersonDTO, Void> query) {
    	log.info("Fetching page: "+query.getPage()+" PageSize: "+query.getPageSize());
        return personRepository.findAll(Pageable.ofSize(query.getPageSize()).withPage(query.getPage())).stream().map(personDTOConverter::convertToDTO);
    }

    @Override
    public Stream<PersonDTO> listByFilter(Query<PersonDTO, PersonFilterDTO> query) {
        if (hasEmailFilter(query.getFilter())) {
            return personRepository.findAllByEmail(query.getFilter().get().getEmail(), Pageable.ofSize(query.getPageSize()).withPage(query.getPage())).stream().map(personDTOConverter::convertToDTO);
        } else {
            return personRepository.findAll(Pageable.ofSize(query.getPageSize()).withPage(query.getPage())).stream().map(personDTOConverter::convertToDTO);
        }
    }

    @Override
    public Optional<PersonDTO> findById(Long personId) {
        return personRepository.findById(personId).map(personDTOConverter::convertToDTO);
    }

    @Override
    public PersonDTO save(PersonDTO dto) {
		/*
		 * Random random = new Random(); if(random.nextInt(5)<4) { throw new
		 * RuntimeException("test Exception"); }
		 */
        return personDTOConverter.convertToDTO(personRepository.saveAndFlush(personDTOConverter.convertToEntity(this::findEntity, dto)));
    }

    private boolean hasEmailFilter(Optional<PersonFilterDTO> filterDTO) {
        return filterDTO.isPresent() && filterDTO.get().getEmail() != null && !filterDTO.get().getEmail().isEmpty();
    }

    private Person findEntity(PersonDTO item) {
        if (item.getId() == null) {
            return new Person();
        }
        Optional<Person> existingEntity = personRepository.findById(item.getId());
        if (!existingEntity.isPresent()) {
            throw new RuntimeException("Attempt to modify an entity that does not exist");
        }
        return existingEntity.get();
    }

	@Override
	public Stream<PersonDTO> listBySingleFilter(Query<PersonDTO, String> query) {
		throw new UnsupportedOperationException("Not implemented");
	}
}
