package com.example.application.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.example.application.dto.PersonDTO;
import com.example.application.dto.PersonFilterDTO;
import com.example.application.entities.Person;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.data.provider.SortDirection;

@Repository
public class PersonRepositoryImpl implements PersonRepositoryCustom {

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<Person> findAllByFilter(PersonFilterDTO personFilter, Pageable pageable,GridSortOrder<PersonDTO> sortOrder) {
		Query query = entityManager.createNativeQuery(getDynamicQuery(personFilter,sortOrder), Person.class);
		query.setFirstResult((pageable.getPageNumber()) * pageable.getPageSize());
		query.setMaxResults(pageable.getPageSize());
		return query.getResultList();
	}

	public String getDynamicQuery(PersonFilterDTO personFilter,GridSortOrder<PersonDTO> sortOrder) {
		String query = "select * from person as person ";
		int count = 0;
		if (personFilter.getFirstName() != null && !personFilter.getFirstName().isEmpty()) {
			query = query.concat("where person.firstname LIKE '%" + personFilter.getFirstName() + "%'");
			count++;
		}
		if (personFilter.getLastName() != null && !personFilter.getLastName().isEmpty()) {
			if (count > 0) {
				query = query.concat(" and ");
			}else {
				query = query.concat(" where ");
			}
			query = query.concat("person.lastname LIKE '%" + personFilter.getLastName() + "%'");
			count++;
		}
		if (personFilter.getEmail() != null && !personFilter.getEmail().isEmpty()) {
			if (count > 0) {
				query = query.concat(" and ");
			}else {
				query = query.concat(" where ");
			}
			query = query.concat("person.email LIKE '%" + personFilter.getEmail() + "%'");
			count++;
		}
		if (personFilter.getOccupation() != null && !personFilter.getOccupation().isEmpty()) {
			if (count > 0) {
				query = query.concat(" and ");
			}else {
				query = query.concat(" where ");
			}
			query = query.concat("person.occupation LIKE '%" + personFilter.getOccupation() + "%'");
			count++;
		}
		if (personFilter.getFavoriteFood() != null && !personFilter.getFavoriteFood().isEmpty()) {
			if (count > 0) {
				query = query.concat(" and ");
			}else {
				query = query.concat(" where ");
			}
			query = query.concat("person.favoritefood LIKE '%" + personFilter.getFavoriteFood() + "%'");
			count++;
		}
		
		if(sortOrder!=null) {
			query = query.concat(" order by person." + sortOrder.getSorted().getKey());
			if(sortOrder.getDirection() == SortDirection.DESCENDING) {
				query = query.concat(" desc");
			}else {
				query = query.concat(" asc");
			}
		}
		return query;
	}

}
