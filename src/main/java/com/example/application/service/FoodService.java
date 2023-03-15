package com.example.application.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.application.dto.FoodDTO;
import com.example.application.dto.FoodFilterDTO;
import com.example.application.entities.Food;
import com.example.application.repositories.FoodRepository;
import com.vaadin.flow.data.provider.Query;

@Service
public class FoodService implements DataService<FoodDTO, FoodFilterDTO> {

	@Autowired
	private FoodRepository foodRepository;

	@Autowired
	private FoodDTOConverter foodDTOConverter;

	@Override
	public Stream<FoodDTO> list(Query<FoodDTO, Void> query) {
		// TODO use logger
		System.out.println("Fetching page: " + query.getPage() + " PageSize: " + query.getPageSize());
		return foodRepository.findAll(Pageable.ofSize(query.getPageSize()).withPage(query.getPage())).stream()
				.map(foodDTOConverter::convertToDTO);

	}

	@Override
	public List<FoodDTO> listByFilter(Query<FoodDTO, FoodFilterDTO> query) {
		if (hasNameFilter(query.getFilter())) {
			return foodRepository
					.findAllByName(query.getFilter().get().getName(),
							Pageable.ofSize(query.getPageSize()).withPage(query.getPage()))
					.stream().map(foodDTOConverter::convertToDTO).collect(Collectors.toList());
		} else {
			return foodRepository.findAll(Pageable.ofSize(query.getPageSize()).withPage(query.getPage())).stream()
					.map(foodDTOConverter::convertToDTO).collect(Collectors.toList());
		}

	}

	@Override
	public Optional<FoodDTO> findById(Long fooId) {
		return foodRepository.findById(fooId).map(foodDTOConverter::convertToDTO);
	}

	@Override
	public FoodDTO save(FoodDTO dto) {
		return foodDTOConverter
				.convertToDTO(foodRepository.saveAndFlush(foodDTOConverter.convertToEntity(this::findEntity, dto)));

	}

	private boolean hasNameFilter(Optional<FoodFilterDTO> filterDTO) {
		return filterDTO.isPresent() && filterDTO.get().getName() != null && !filterDTO.get().getName().isEmpty();
	}

	private Food findEntity(FoodDTO item) {
		if (item.getId() == null) {
			return new Food();
		}
		Optional<Food> existingEntity = foodRepository.findById(item.getId());
		if (!existingEntity.isPresent()) {
			throw new RuntimeException("Attempt to modify an entity that does not exist");
		}
		return existingEntity.get();
	}
}
