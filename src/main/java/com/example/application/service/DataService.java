package com.example.application.service;

import com.example.application.dto.PersonDTO;
import com.example.application.dto.PersonFilterDTO;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.data.provider.Query;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.domain.PageRequest;

/**
 * {@link DataService} is able to fetch a page of data of specific type
 * matching provided filter.
 *
 * @param <DTO>    type of the data
 * @param <FILTER> type of the filter
 */
public interface DataService<DTO, FILTER> {

	/**
	 * Provides data for the given query
	 * 
	 * @param query
	 * @return list of found items
	 */
	Stream<DTO> list(Query<DTO, Void> query);

	Optional<DTO> findById(Long personId);

	DTO save(DTO dto);
	
	Stream<DTO> listBySingleFilter(Query<DTO, String> query);
	
	Stream<PersonDTO> findAllByFilter(PersonFilterDTO personFilter, PageRequest pageRequest, GridSortOrder<PersonDTO> sortOrder);
}
