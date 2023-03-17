package com.example.application.service;

import com.example.application.dto.OccupationDTO;
import com.example.application.dto.PersonDTO;
import com.example.application.entities.Person;
import org.atmosphere.inject.annotation.ApplicationScoped;
import org.springframework.stereotype.Component;

@ApplicationScoped
@Component
public class PersonDTOConverter implements DTOConverter<Person, PersonDTO> {

    @Override
    public PersonDTO convertToDTO(Person entity) {
        if (entity == null) {
            return null;
        }
        PersonDTO dto = new PersonDTO();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setEmail(entity.getEmail());
        
        //TODO
      //dto.setOccupation(entity.getOccupation());
      // dto.setFavoriteFood(entity.getFavoriteFood());

        return dto;
    }

    @Override
    public Person convertToEntity(EntityProvider<PersonDTO, Person> entityProvider, PersonDTO dto) {
        if (dto == null) {
            return null;
        }
        Person entity = entityProvider.apply(dto);
        entity.setId(dto.getId());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        // TODO
        return entity;
    }
}
