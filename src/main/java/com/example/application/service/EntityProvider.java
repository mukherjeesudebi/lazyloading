package com.example.application.service;

import com.example.application.entities.AbstractEntity;

import java.io.Serializable;
import java.util.function.Function;

public interface EntityProvider<DTO, ENTITY extends AbstractEntity> extends Function<DTO, ENTITY>, Serializable {

	@Override
	ENTITY apply(DTO dto);

}
