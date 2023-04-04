package com.example.application.service;

import com.example.application.dto.OccupationDTO;
import com.example.application.dto.OccupationFilterDTO;
import com.example.application.dto.PersonDTO;
import com.example.application.dto.PersonFilterDTO;
import com.example.application.entities.Occupation;
import com.example.application.repositories.OccupationRepository;
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
public class OccupationService implements DataService<OccupationDTO, OccupationFilterDTO> {
	private static final Logger log = LoggerFactory.getLogger(PersonService.class);
	
	private final OccupationRepository occupationRepository;

	private final OccupationDTOConverter occupationDTOConverter;

	public OccupationService(@Autowired OccupationRepository occupationRepository, @Autowired OccupationDTOConverter occupationDTOConverter) {
		this.occupationRepository = occupationRepository;
		this.occupationDTOConverter = occupationDTOConverter;
	}

	@Override
	public Stream<OccupationDTO> list(Query<OccupationDTO, Void> query) {
		log.info("Fetching page: {}, PageSize: {}", query.getPage(), query.getPageSize());
		return occupationRepository.findAll(Pageable.ofSize(query.getPageSize()).withPage(query.getPage())).stream()
				.map(occupationDTOConverter::convertToDTO);
	}

	@Override
	public Optional<OccupationDTO> findById(Long occupationId) {
		return occupationRepository.findById(occupationId).map(occupationDTOConverter::convertToDTO);
	}

	@Override
	public OccupationDTO save(OccupationDTO dto) {
		return occupationDTOConverter.convertToDTO(
				occupationRepository.saveAndFlush(occupationDTOConverter.convertToEntity(this::findEntity, dto)));
	}

	@Override
	public Stream<OccupationDTO> listBySingleFilter(Query<OccupationDTO, String> query) {
		if (hasNameFilter(query.getFilter())) {
			log.info("Fetching page: {}, PageSize: {}, Filter: {}", query.getPage(), query.getPageSize(), query.getFilter());
			return occupationRepository
					.findAllByNameContainingIgnoreCase(query.getFilter().get(),
							Pageable.ofSize(query.getPageSize()).withPage(query.getPage()))
					.stream().map(occupationDTOConverter::convertToDTO);
		} else {
			log.info("Fetching page: {}, PageSize: {}", query.getPage(), query.getPageSize());
			return occupationRepository.findAll(Pageable.ofSize(query.getPageSize()).withPage(query.getPage())).stream()
					.map(occupationDTOConverter::convertToDTO);
		}
	}

	@Override
	public Stream<OccupationDTO> listByFilter(Query<OccupationDTO, OccupationFilterDTO> query) {
		throw new UnsupportedOperationException("Not implemented");
	}

	private boolean hasNameFilter(Optional<String> filter) {
		return filter.filter(s -> !s.isEmpty()).isPresent();
	}

	private Occupation findEntity(OccupationDTO item) {
		if (item.getId() == null) {
			return new Occupation();
		}
		Optional<Occupation> existingEntity = occupationRepository.findById(item.getId());
		if (existingEntity.isEmpty()) {
			throw new RuntimeException("Attempt to modify an entity that does not exist");
		}
		return existingEntity.get();
	}

	@Override
	public Stream<PersonDTO> findAllByFilter(PersonFilterDTO personFilter, PageRequest pageRequest,
			GridSortOrder<PersonDTO> sortOrder) {
		throw new UnsupportedOperationException("Not implemented");
	}

}
