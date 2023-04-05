package com.example.application.service;

import java.util.Optional;

import org.atmosphere.inject.annotation.ApplicationScoped;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.application.dto.FoodDTO;
import com.example.application.dto.OccupationDTO;
import com.example.application.dto.PersonDTO;
import com.example.application.entities.Food;
import com.example.application.entities.Occupation;
import com.example.application.entities.Person;
import com.example.application.repositories.FoodRepository;
import com.example.application.repositories.OccupationRepository;

@ApplicationScoped
@Component
public class PersonDTOConverter implements DTOConverter<Person, PersonDTO> {
	
	@Autowired
	private OccupationRepository occupationRepository;
	
	@Autowired
	private FoodRepository foodRepository;

    @Override
    public PersonDTO convertToDTO(Person entity) {
        if (entity == null) {
            return null;
        }
        PersonDTO dto = new PersonDTO();
        dto.setId(entity.getId());
        dto.setConsistencyVersion(entity.getConsistencyVersion());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setEmail(entity.getEmail());
        
        OccupationDTOConverter occuupationdtoConverter = new OccupationDTOConverter();
        dto.setOccupation(occuupationdtoConverter.convertToDTO(entity.getOccupation()));
        
        FoodDTOConverter foodDTOConverter = new FoodDTOConverter();
        dto.setFavoriteFood(foodDTOConverter.convertToDTO(entity.getFavoriteFood()));

        return dto;
    }

    @Override
    public Person convertToEntity(EntityProvider<PersonDTO, Person> entityProvider, PersonDTO dto) {
        if (dto == null) {
            return null;
        }
        Person entity = entityProvider.apply(dto);
        entity.setId(dto.getId());
        entity.setConsistencyVersion(dto.getConsistencyVersion());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        // TODO
        OccupationDTOConverter occuupationdtoConverter = new OccupationDTOConverter();
        entity.setOccupation(occuupationdtoConverter.convertToEntity(this::findOccupationEntity, dto.getOccupation()));
        
        FoodDTOConverter foodDTOConverter = new FoodDTOConverter();
        entity.setFavoriteFood(foodDTOConverter.convertToEntity(this::findFoodEntity, dto.getFavoriteFood()));
        return entity;
    }
    
    private Occupation findOccupationEntity(OccupationDTO item) {
        if (item.getId() == null) {
            return new Occupation();
        }
        Optional<Occupation> existingEntity = occupationRepository.findById(item.getId());
        if (!existingEntity.isPresent()) {
            throw new RuntimeException("Attempt to modify an entity that does not exist");
        }
        return existingEntity.get();
    }
    

    private Food findFoodEntity(FoodDTO item) {
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
