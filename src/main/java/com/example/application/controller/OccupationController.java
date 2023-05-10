package com.example.application.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.application.dto.OccupationDTO;
import com.example.application.dto.PageableRestClientDTO;
import com.example.application.entities.Occupation;
import com.example.application.repositories.OccupationRepository;
import com.example.application.service.OccupationDTOConverter;

@RestController
public class OccupationController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PersonController.class);

	private final OccupationRepository occupationRepository;

	private final OccupationDTOConverter occupationDTOConverter;;

	public OccupationController(@Autowired OccupationRepository occupationRepository,
			@Autowired OccupationDTOConverter occupationDTOConverter) {
		this.occupationRepository = occupationRepository;
		this.occupationDTOConverter = occupationDTOConverter;
	}

	@PostMapping("/occupationList")
	public List<OccupationDTO> list(@RequestBody PageableRestClientDTO data) {
		return occupationRepository.findAll(data.toPageable()).stream().map(occupationDTOConverter::convertToDTO)
				.toList();
	}

	@PostMapping("/findOccupationById")
	public Optional<OccupationDTO> findById(Long occupationId) {
		return occupationRepository.findById(occupationId).map(occupationDTOConverter::convertToDTO);
	}

	@PostMapping("/saveOccupation")
	public OccupationDTO save(OccupationDTO dto) {
		return occupationDTOConverter.convertToDTO(
				occupationRepository.saveAndFlush(occupationDTOConverter.convertToEntity(this::findEntity, dto)));
	}

	@PostMapping("/findAllByOccNameContainingIgnoreCase")
	public List<OccupationDTO> findAllByNameContainingIgnoreCase(@RequestBody PageableRestClientDTO data) {
		return occupationRepository.findAllByNameContainingIgnoreCase(data.getFilterName(), data.toPageable()).stream()
				.map(occupationDTOConverter::convertToDTO).toList();
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
}
