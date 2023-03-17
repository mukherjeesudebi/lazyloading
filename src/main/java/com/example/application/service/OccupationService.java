package com.example.application.service;

import java.util.Optional;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.application.dto.OccupationDTO;
import com.example.application.dto.OccupationFilterDTO;
import com.example.application.entities.Occupation;
import com.example.application.repositories.OccupationRepository;
import com.vaadin.flow.data.provider.Query;

@Service
public class OccupationService implements DataService<OccupationDTO, OccupationFilterDTO> {
	private static final Logger log = LoggerFactory.getLogger(PersonService.class);
	
	@Autowired
	private OccupationRepository occupationRepository;

	@Autowired
	private OccupationDTOConverter occupationDTOConverter;

	@Override
	public Stream<OccupationDTO> list(Query<OccupationDTO, Void> query) {
		log.info("Fetching page: " + query.getPage() + " PageSize: " + query.getPageSize());
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
			return occupationRepository
					.findAllByNameContainingIgnoreCase(query.getFilter().get(),
							Pageable.ofSize(query.getPageSize()).withPage(query.getPage()))
					.stream().map(occupationDTOConverter::convertToDTO);
		} else {
			return occupationRepository.findAll(Pageable.ofSize(query.getPageSize()).withPage(query.getPage())).stream()
					.map(occupationDTOConverter::convertToDTO);
		}
	}

	@Override
	public Stream<OccupationDTO> listByFilter(Query<OccupationDTO, OccupationFilterDTO> query) {
		throw new UnsupportedOperationException("Not implemented");
	}

	private boolean hasNameFilter(Optional<String> filter) {
		return filter.isPresent() && filter.get() != null && !filter.get().isEmpty();
	}

	private Occupation findEntity(OccupationDTO item) {
		if (item.getId() == null) {
			return new Occupation();
		}
		Optional<Occupation> existingEntity = occupationRepository.findById(item.getId());
		if (!existingEntity.isPresent()) {
			throw new RuntimeException("Attempt to modify an entity that does not exist");
		}
		return existingEntity.get();
	}

}
