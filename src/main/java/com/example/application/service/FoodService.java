package com.example.application.service;

import com.example.application.dto.FoodDTO;
import com.example.application.dto.FoodFilterDTO;
import com.example.application.entities.Food;
import com.example.application.repositories.FoodRepository;
import com.vaadin.flow.data.provider.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Service
public class FoodService implements DataService<FoodDTO, FoodFilterDTO> {
	private static final Logger log = LoggerFactory.getLogger(PersonService.class);

	private final FoodRepository foodRepository;

	private final FoodDTOConverter foodDTOConverter;

	public FoodService(@Autowired FoodRepository foodRepository, @Autowired FoodDTOConverter foodDTOConverter) {
		this.foodRepository = foodRepository;
		this.foodDTOConverter = foodDTOConverter;
	}

	@Override
	public Stream<FoodDTO> list(Query<FoodDTO, Void> query) {
		log.info("Fetching page: {}, PageSize: {}", query.getPage(), query.getPageSize());
		return foodRepository.findAll(Pageable.ofSize(query.getPageSize()).withPage(query.getPage())).stream()
				.map(foodDTOConverter::convertToDTO);

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

	@Override
	public Stream<FoodDTO> listBySingleFilter(Query<FoodDTO, String> query) {
		if (hasNameFilter(query.getFilter())) {
			log.info("Fetching page: {}, PageSize: {}, Filter: {}", query.getPage(), query.getPageSize(), query.getFilter());
			return foodRepository
					.findAllByNameContainingIgnoreCase(query.getFilter().get(),
							Pageable.ofSize(query.getPageSize()).withPage(query.getPage()))
					.stream().map(foodDTOConverter::convertToDTO);
		} else {
			log.info("Fetching page: {}, PageSize: {}", query.getPage(), query.getPageSize());
			return foodRepository.findAll(Pageable.ofSize(query.getPageSize()).withPage(query.getPage())).stream()
					.map(foodDTOConverter::convertToDTO);
		}

	}

	@Override
	public Stream<FoodDTO> listByFilter(Query<FoodDTO, FoodFilterDTO> query) {
		throw new UnsupportedOperationException("Not implemented");
	}

	private boolean hasNameFilter(Optional<String> filter) {
		return filter.filter(s -> !s.isEmpty()).isPresent();
	}

	private Food findEntity(FoodDTO item) {
		if (item.getId() == null) {
			return new Food();
		}
		Optional<Food> existingEntity = foodRepository.findById(item.getId());
		if (existingEntity.isEmpty()) {
			throw new RuntimeException("Attempt to modify an entity that does not exist");
		}
		return existingEntity.get();
	}
}
