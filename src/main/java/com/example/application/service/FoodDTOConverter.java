package com.example.application.service;

import org.atmosphere.inject.annotation.ApplicationScoped;
import org.springframework.stereotype.Component;

import com.example.application.dto.FoodDTO;
import com.example.application.entities.Food;

@ApplicationScoped
@Component
public class FoodDTOConverter implements DTOConverter<Food, FoodDTO> {

	@Override
	public FoodDTO convertToDTO(Food entity) {
		if (entity == null) {
			return null;
		}
		FoodDTO dto = new FoodDTO();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		return dto;

	}

	@Override
	public Food convertToEntity(EntityProvider<FoodDTO, Food> entityProvider, FoodDTO dto) {
		if (dto == null) {
			return null;
		}
		Food entity = entityProvider.apply(dto);
		entity.setId(dto.getId());
		entity.setName(dto.getName());
		return entity;

	}

}
