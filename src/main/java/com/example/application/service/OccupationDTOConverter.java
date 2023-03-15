package com.example.application.service;

import org.atmosphere.inject.annotation.ApplicationScoped;

import org.springframework.stereotype.Component;

import com.example.application.dto.OccuptionDTO;
import com.example.application.entities.Occupation;


@ApplicationScoped
@Component
public class OccupationDTOConverter implements DTOConverter<Occupation, OccuptionDTO> {

	@Override
	public OccuptionDTO convertToDTO(Occupation entity) {
		if (entity == null) {
			return null;
		}
		OccuptionDTO dto = new OccuptionDTO();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		return dto;

	}

	@Override
	public Occupation convertToEntity(EntityProvider<OccuptionDTO, Occupation> entityProvider, OccuptionDTO dto) {
		if (dto == null) {
			return null;
		}
		Occupation entity = entityProvider.apply(dto);
		entity.setId(dto.getId());
		entity.setName(dto.getName());
		return entity;

	}

}
