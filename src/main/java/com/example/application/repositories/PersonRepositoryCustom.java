package com.example.application.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.example.application.dto.PersonDTO;
import com.example.application.dto.PersonFilterDTO;
import com.example.application.entities.Person;
import com.vaadin.flow.component.grid.GridSortOrder;

public interface PersonRepositoryCustom {
	List<Person> findAllByFilter(PersonFilterDTO personFilter,Pageable pageable,GridSortOrder<PersonDTO> sortOrder);
}
