package com.example.application.service;

import org.atmosphere.inject.annotation.ApplicationScoped;

import org.springframework.stereotype.Component;

import com.example.application.dto.OccupationDTO;
import com.example.application.entities.Occupation;


@ApplicationScoped
@Component
public class OccupationDTOConverter implements DTOConverter<Occupation, OccupationDTO> {

	@Override
	public OccupationDTO convertToDTO(Occupation entity) {
		if (entity == null) {
			return null;
		}
		OccupationDTO dto = new OccupationDTO();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		return dto;

	}

	@Override
	public Occupation convertToEntity(EntityProvider<OccupationDTO, Occupation> entityProvider, OccupationDTO dto) {
		if (dto == null) {
			return null;
		}
		Occupation entity = entityProvider.apply(dto);
		entity.setId(dto.getId());
		entity.setName(dto.getName());
		return entity;

	}

}
