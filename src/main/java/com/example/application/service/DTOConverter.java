package com.example.application.service;

import com.example.application.entities.AbstractEntity;
import java.util.List;
import java.util.stream.Collectors;

/**
 * {@link DTOConverter} takes care of translating between entity and DTO types
 *
 * @param <E>   type of the entity
 * @param <DTO> type of the DTO
 */
public interface DTOConverter<E extends AbstractEntity, DTO> {

    /**
     * Converts an entity to DTO
     *
     * @param entity
     * @return DTO
     */
    DTO convertToDTO(E entity);

    /**
     * Converts a list of entities to a list of DTOs
     *
     * @param entities
     * @return DTO list
     */
    default List<DTO> convertToDTO(List<E> entities) {
        if (entities == null) {
            return null;
        }

        return entities.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    /**
     * Converts a DTO to an entity
     *
     * @param entityProvider is used to fetch an existing database entity as basis
     *                       of the DTO conversion
     * @param dto
     * @return entity fetched by the {@link EntityProvider} updated with
     *         the implementation specific values from the DTO
     */
    E convertToEntity(EntityProvider<DTO, E> entityProvider, DTO dto);

    /**
     * Converts a list of DTOs to a list of entities
     *
     * @param entityProvider is used to fetch an existing database entity as basis
     *                       of the DTO conversion
     * @param dtos
     * @return entities fetched by the {@link EntityProvider} updated with the
     *         implementation specific values from DTOs
     */
    default List<E> convertToEntity(EntityProvider<DTO, E> entityProvider, List<DTO> dtos) {
        if (dtos == null) {
            return null;
        }

        return dtos.stream().map(dto -> this.convertToEntity(entityProvider, dto)).collect(Collectors.toList());
    }
}
