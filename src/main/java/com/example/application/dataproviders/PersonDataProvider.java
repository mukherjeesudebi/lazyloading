package com.example.application.dataproviders;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import com.example.application.dto.PersonDTO;
import com.example.application.dto.PersonFilterDTO;
import com.example.application.entities.Person;
import com.example.application.service.PersonService;
import com.example.application.util.GridSorterProperty;
import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.data.provider.SortDirection;

public class PersonDataProvider extends AbstractBackEndDataProvider<PersonDTO, PersonFilterDTO> {

	private PersonService personService;

	public PersonDataProvider(PersonService personService) {
		this.personService = personService;
	}

	@Override
	protected Stream<PersonDTO> fetchFromBackEnd(Query<PersonDTO, PersonFilterDTO> query) {
		Stream<PersonDTO> stream = personService.listByFilter(query);

		// Filtering
		if (query.getFilter().isPresent()) {
			stream = stream.filter(person -> query.getFilter().get().test(person));
		}

		// Sorting
		if (query.getSortOrders().size() > 0) {
			stream = stream.sorted(sortComparator(query.getSortOrders()));
		}

		// Pagination
		return stream.skip(query.getOffset()).limit(query.getLimit());
	}

	@Override
	protected int sizeInBackEnd(Query<PersonDTO, PersonFilterDTO> query) {
		return (int) fetchFromBackEnd(query).count();
	}

	private static Comparator<PersonDTO> sortComparator(List<QuerySortOrder> sortOrders) {
		return sortOrders.stream().map(sortOrder -> {
			Comparator<PersonDTO> comparator = personFieldComparator(sortOrder.getSorted());

			if (sortOrder.getDirection() == SortDirection.DESCENDING) {
				comparator = comparator.reversed();
			}

			return comparator;
		}).reduce(Comparator::thenComparing).orElse((p1, p2) -> 0);
	}

	private static Comparator<PersonDTO> personFieldComparator(String sorted) {
		if (sorted.equals(GridSorterProperty.FIRSTNAME.label)) {
			return Comparator.comparing(person -> person.getFirstName());
		} else if (sorted.equals(GridSorterProperty.LASTNAME.label)) {
			return Comparator.comparing(person -> person.getLastName());
		} else if (sorted.equals(GridSorterProperty.EMAIL.label)) {
			return Comparator.comparing(person -> person.getEmail());
		} else if (sorted.equals(GridSorterProperty.OCCUPATION.label)) {
			return Comparator
					.comparing(person -> person.getOccupation() != null ? person.getOccupation().toString() : "");
		} else if (sorted.equals(GridSorterProperty.FAVORITEFOOD.label)) {
			return Comparator
					.comparing(person -> person.getFavoriteFood() != null ? person.getFavoriteFood().toString() : "");
		}
		return (p1, p2) -> 0;
	}

	public PersonService getPersonService() {
		return personService;
	}

	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}

}
