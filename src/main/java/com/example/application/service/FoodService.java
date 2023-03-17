package com.example.application.service;

import java.util.Optional;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private static final Logger log = LoggerFactory.getLogger(PersonService.class);

	@Autowired
	private FoodRepository foodRepository;

	@Autowired
	private FoodDTOConverter foodDTOConverter;

	@Override
	public Stream<FoodDTO> list(Query<FoodDTO, Void> query) {
		log.info("Fetching page: " + query.getPage() + " PageSize: " + query.getPageSize());
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
			return foodRepository
					.findAllByNameContainingIgnoreCase(query.getFilter().get(),
							Pageable.ofSize(query.getPageSize()).withPage(query.getPage()))
					.stream().map(foodDTOConverter::convertToDTO);
		} else {
			return foodRepository.findAll(Pageable.ofSize(query.getPageSize()).withPage(query.getPage())).stream()
					.map(foodDTOConverter::convertToDTO);
		}

	}

	@Override
	public Stream<FoodDTO> listByFilter(Query<FoodDTO, FoodFilterDTO> query) {
		throw new UnsupportedOperationException("Not implemented");
	}

	private boolean hasNameFilter(Optional<String> filter) {
		return filter.isPresent() && filter.get() != null && !filter.get().isEmpty();
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
