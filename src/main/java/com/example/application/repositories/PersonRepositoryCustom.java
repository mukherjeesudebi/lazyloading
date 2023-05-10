package com.example.application.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.example.application.dto.PersonDTO;
import com.example.application.dto.PersonFilterDTO;
import com.example.application.entities.Person;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.data.provider.SortDirection;

public interface PersonRepositoryCustom {
	List<Person> findAllByFilter(Pageable pageable,PersonFilterDTO personFilter,String sortKey,SortDirection direction);
}
