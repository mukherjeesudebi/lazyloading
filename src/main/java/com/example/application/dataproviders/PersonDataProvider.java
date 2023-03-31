package com.example.application.dataproviders;

import java.util.stream.Stream;

import com.example.application.dto.PersonDTO;
import com.example.application.dto.PersonFilterDTO;
import com.example.application.service.PersonService;
import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;

public class PersonDataProvider extends AbstractBackEndDataProvider<PersonDTO, PersonFilterDTO> {

	private PersonService personService;
	
	public PersonDataProvider(PersonService personService) {
		this.personService = personService;
	}

	@Override
	protected Stream<PersonDTO> fetchFromBackEnd(Query<PersonDTO, PersonFilterDTO> query) {
		Stream<PersonDTO> stream = personService.listByFilter(query);
		
		if (query.getFilter().isPresent()) {
            stream = stream.filter(person -> query.getFilter().get().test(person));
        }

		return stream.skip(query.getOffset()).limit(query.getLimit());
	}

	@Override
	protected int sizeInBackEnd(Query<PersonDTO, PersonFilterDTO> query) {
		return (int) fetchFromBackEnd(query).count();
	}

	public PersonService getPersonService() {
		return personService;
	}

	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}

}
