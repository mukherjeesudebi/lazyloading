package com.example.application.service;

import com.example.application.dto.PersonDTO;
import com.example.application.dto.PersonFilterDTO;
import com.example.application.entities.Person;
import com.example.application.repositories.PersonRepository;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.data.provider.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Service
public class PersonService implements DataService<PersonDTO, PersonFilterDTO> {
	
	private static final Logger log = LoggerFactory.getLogger(PersonService.class);

    private final PersonRepository personRepository;
    private final PersonDTOConverter personDTOConverter;

    public PersonService(@Autowired PersonRepository personRepository, @Autowired PersonDTOConverter personDTOConverter) {
        this.personRepository = personRepository;
        this.personDTOConverter = personDTOConverter;
    }

    @Override
    public Stream<PersonDTO> list(Query<PersonDTO, Void> query) {
        log.info("Fetching page: {}, PageSize: {}", query.getPage(), query.getPageSize());
        return personRepository.findAll(Pageable.ofSize(query.getPageSize()).withPage(query.getPage())).stream().map(personDTOConverter::convertToDTO);
    }
    
    @Override
    public Stream<PersonDTO> findAllByFilter(PersonFilterDTO personFilter, PageRequest pageRequest, GridSortOrder<PersonDTO> sortOrder) {
    	return personRepository.findAllByFilter(personFilter,pageRequest,sortOrder).stream().map(personDTOConverter::convertToDTO);
    }

    @Override
    public Optional<PersonDTO> findById(Long personId) {
        return personRepository.findById(personId).map(personDTOConverter::convertToDTO);
    }

    @Override
    public PersonDTO save(PersonDTO dto) {
        return personDTOConverter.convertToDTO(personRepository.saveAndFlush(personDTOConverter.convertToEntity(this::findEntity, dto)));
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

	@Override
	public Stream<PersonDTO> listBySingleFilter(Query<PersonDTO, String> query) {
		throw new UnsupportedOperationException("Not implemented");
	}
}
