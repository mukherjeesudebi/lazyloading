package com.example.application.service;

import com.example.application.dto.PersonDTO;
import com.vaadin.flow.data.provider.Query;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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

	/**
	 * Provides data for the given query
	 *
	 * @param query
	 * @return list of found items
	 */
	List<DTO> listByFilter(Query<DTO, FILTER> query);

	Optional<DTO> findById(Long personId);

	PersonDTO save(DTO dto);
}
