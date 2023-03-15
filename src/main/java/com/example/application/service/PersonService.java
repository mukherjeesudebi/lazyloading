package com.example.application.service;

import com.example.application.dto.PersonDTO;
import com.example.application.dto.PersonFilterDTO;
import com.example.application.entities.Person;
import com.example.application.repositories.PersonRepository;
import com.vaadin.flow.data.provider.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PersonService implements DataService<PersonDTO, PersonFilterDTO> {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonDTOConverter personDTOConverter;

    @Override
    public Stream<PersonDTO> list(Query<PersonDTO, Void> query) {
        // TODO use logger
        System.out.println("Fetching page: "+query.getPage()+" PageSize: "+query.getPageSize());
        return personRepository.findAll(Pageable.ofSize(query.getPageSize()).withPage(query.getPage())).stream().map(personDTOConverter::convertToDTO);
    }

    @Override
    public List<PersonDTO> listByFilter(Query<PersonDTO, PersonFilterDTO> query) {
        if (hasEmailFilter(query.getFilter())) {
            return personRepository.findAllByEmail(query.getFilter().get().getEmail(), Pageable.ofSize(query.getPageSize()).withPage(query.getPage())).stream().map(personDTOConverter::convertToDTO).collect(Collectors.toList());
        } else {
            return personRepository.findAll(Pageable.ofSize(query.getPageSize()).withPage(query.getPage())).stream().map(personDTOConverter::convertToDTO).collect(Collectors.toList());
        }
    }

    @Override
    public Optional<PersonDTO> findById(Long personId) {
        return personRepository.findById(personId).map(personDTOConverter::convertToDTO);
    }

    @Override
    public PersonDTO save(PersonDTO dto) {
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
}
