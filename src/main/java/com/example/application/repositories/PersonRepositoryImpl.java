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

	@SuppressWarnings("unchecked")
	public List<Person> findAllByFilter(Pageable pageable,PersonFilterDTO personFilter,String sortKey,SortDirection direction) {
		Query query = entityManager.createNativeQuery(getDynamicQuery(personFilter,sortKey,direction), Person.class);
		query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
		query.setMaxResults(pageable.getPageSize());
		return query.getResultList();
	}
	
	public String getDynamicQuery(PersonFilterDTO personFilter, String sortKey,SortDirection direction) {
		String query = "select * from person as person ";
		int count = 0;
		if (personFilter.getFirstName() != null && !personFilter.getFirstName().isEmpty()) {
			query = query.concat("where person.firstname LIKE '%" + personFilter.getFirstName() + "%'");
			count++;
		}
		if (personFilter.getLastName() != null && !personFilter.getLastName().isEmpty()) {
			if (count > 0) {
				query = query.concat(" and ");
			} else {
				query = query.concat(" where ");
			}
			query = query.concat("person.lastname LIKE '%" + personFilter.getLastName() + "%'");
			count++;
		}
		if (personFilter.getEmail() != null && !personFilter.getEmail().isEmpty()) {
			if (count > 0) {
				query = query.concat(" and ");
			} else {
				query = query.concat(" where ");
			}
			query = query.concat("person.email LIKE '%" + personFilter.getEmail() + "%'");
			count++;
		}
		if (personFilter.getOccupation() != null && !personFilter.getOccupation().isEmpty()) {
			if (count > 0) {
				query = query.concat(" and ");
			} else {
				query = query.concat(" where ");
			}
			query = query.concat("person.occupation LIKE '%" + personFilter.getOccupation() + "%'");
			count++;
		}
		if (personFilter.getFavoriteFood() != null && !personFilter.getFavoriteFood().isEmpty()) {
			if (count > 0) {
				query = query.concat(" and ");
			} else {
				query = query.concat(" where ");
			}
			query = query.concat("person.favoritefood LIKE '%" + personFilter.getFavoriteFood() + "%'");
			count++;
		}
		if (direction != null) {
			query = query.concat(" order by person." + sortKey);
			if (direction == SortDirection.DESCENDING) {
				query = query.concat(" desc");
			} else {
				query = query.concat(" asc");
			}
		}
		return query;
	}

	/*
	 * 
	 * This method uses CriteraBuilder to filter and sort data at query level. this
	 * is an alternative to using native query
	 * 
	 * 
	 * @Override public List<Person> findAllByFilter(PersonFilterDTO personFilter,
	 * Pageable pageable, GridSortOrder<PersonDTO> sortOrder) { CriteriaBuilder
	 * criteriaBuilder = entityManager.getCriteriaBuilder(); CriteriaQuery<Person>
	 * criteriaQuery = criteriaBuilder.createQuery(Person.class); Root<Person>
	 * personRoot = criteriaQuery.from(Person.class);
	 * 
	 * List<Predicate> predicateList = new ArrayList<>();
	 * 
	 * if (personFilter.getFirstName() != null &&
	 * !personFilter.getFirstName().isEmpty()) { String likeStr =
	 * "%".concat(personFilter.getFirstName()).concat("%");
	 * predicateList.add(criteriaBuilder.like(personRoot.get("firstName"),
	 * likeStr)); } if (personFilter.getLastName() != null &&
	 * !personFilter.getLastName().isEmpty()) { String likeStr =
	 * "%".concat(personFilter.getLastName()).concat("%");
	 * predicateList.add(criteriaBuilder.like(personRoot.get("lastName"), likeStr));
	 * } if (personFilter.getEmail() != null && !personFilter.getEmail().isEmpty())
	 * { String likeStr = "%".concat(personFilter.getEmail()).concat("%");
	 * predicateList.add(criteriaBuilder.like(personRoot.get("email"), likeStr)); }
	 * if (personFilter.getOccupation() != null &&
	 * !personFilter.getOccupation().isEmpty()) { String likeStr =
	 * "%".concat(personFilter.getOccupation()).concat("%");
	 * predicateList.add(criteriaBuilder.like(personRoot.join("occupation",JoinType.
	 * LEFT).get("name"), likeStr)); } if (personFilter.getFavoriteFood() != null &&
	 * !personFilter.getFavoriteFood().isEmpty()) { String likeStr =
	 * "%".concat(personFilter.getFavoriteFood()).concat("%");
	 * predicateList.add(criteriaBuilder.like(personRoot.join("favoriteFood",
	 * JoinType.LEFT).get("name").as(String.class), likeStr)); }
	 * 
	 * criteriaQuery.select(personRoot).where(predicateList.toArray(new
	 * Predicate[0]));
	 * 
	 * if (sortOrder != null) { if (sortOrder.getDirection() ==
	 * SortDirection.DESCENDING) {
	 * criteriaQuery.orderBy(criteriaBuilder.desc(personRoot.get(sortOrder.getSorted
	 * ().getKey()))); } else {
	 * criteriaQuery.orderBy(criteriaBuilder.asc(personRoot.get(sortOrder.getSorted(
	 * ).getKey()))); } }
	 * 
	 * TypedQuery<Person> typedQuery = entityManager.createQuery(criteriaQuery);
	 * typedQuery.setFirstResult((pageable.getPageNumber()) *
	 * pageable.getPageSize()); typedQuery.setMaxResults(pageable.getPageSize());
	 * 
	 * return typedQuery.getResultList(); }
	 * 
	 */

}
