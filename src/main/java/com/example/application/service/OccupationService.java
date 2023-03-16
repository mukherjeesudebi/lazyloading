package com.example.application.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.application.dto.OccuptionDTO;
import com.example.application.dto.OccuptionFilterDTO;
import com.example.application.entities.Occupation;
import com.example.application.repositories.OccupationRepository;
import com.vaadin.flow.data.provider.Query;

@Service
public class OccupationService implements DataService<OccuptionDTO, OccuptionFilterDTO> {

	@Autowired
	private OccupationRepository occupationRepository;

	@Autowired
	private OccupationDTOConverter occupationDTOConverter;

	@Override
	public Stream<OccuptionDTO> list(Query<OccuptionDTO, Void> query) {
		// TODO use logger
		System.out.println("Fetching page: " + query.getPage() + " PageSize: " + query.getPageSize());
		return occupationRepository.findAll(Pageable.ofSize(query.getPageSize()).withPage(query.getPage())).stream()
				.map(occupationDTOConverter::convertToDTO);
	}

	@Override
	public Optional<OccuptionDTO> findById(Long occupationId) {
		return occupationRepository.findById(occupationId).map(occupationDTOConverter::convertToDTO);
	}

	@Override
	public OccuptionDTO save(OccuptionDTO dto) {
		return occupationDTOConverter.convertToDTO(
				occupationRepository.saveAndFlush(occupationDTOConverter.convertToEntity(this::findEntity, dto)));
	}

	@Override
	public Stream<OccuptionDTO> listBySingleFilter(Query<OccuptionDTO, String> query) {
		if (hasNameFilter(query.getFilter())) {
			return occupationRepository
					.findAllByName(query.getFilter().get(),
							Pageable.ofSize(query.getPageSize()).withPage(query.getPage()))
					.stream().map(occupationDTOConverter::convertToDTO);
		} else {
			return occupationRepository.findAll(Pageable.ofSize(query.getPageSize()).withPage(query.getPage())).stream()
					.map(occupationDTOConverter::convertToDTO);
		}
	}

	@Override
	public Stream<OccuptionDTO> listByFilter(Query<OccuptionDTO, OccuptionFilterDTO> query) {
		// TODO Auto-generated method stub
		return null;
	}

	private boolean hasNameFilter(Optional<String> filter) {
		return filter.isPresent() && filter.get() != null && !filter.get().isEmpty();
	}

	private Occupation findEntity(OccuptionDTO item) {
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
