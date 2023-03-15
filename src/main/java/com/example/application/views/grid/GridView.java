package com.example.application.views.grid;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;

import com.example.application.entities.Person;
import com.example.application.repositories.PersonRepository;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Grid")
@Route(value = "grid-view", layout = MainLayout.class)
public class GridView extends HorizontalLayout {

	private PersonRepository personRepository;

	public GridView(PersonRepository personRepository) {
		this.personRepository = personRepository;
		Grid<Person> personsGrid = new Grid<>(Person.class, false);
		
		personsGrid.addColumn(Person::getFirstName).setHeader("First name");
		personsGrid.addColumn(Person::getLastName).setHeader("Last name");
		personsGrid.addColumn(Person::getEmail).setHeader("Email");
		personsGrid.addColumn(Person::getOccupation).setHeader("Occupation");
		personsGrid.addColumn(Person::getFavoriteFood).setHeader("Favorite food");
		
		personsGrid.setItems(query -> {
			return personRepository.findAll(
					PageRequest.of(query.getPage(), query.getPageSize())
					).stream();
		});
		
		personsGrid.addSelectionListener(selection -> {
            Optional<Person> optionalPerson = selection.getFirstSelectedItem();
            if (optionalPerson.isPresent()) {
            	String url = "/editPersonDetails/" + optionalPerson.get().getId();
              UI.getCurrent().getPage().open(url);
            }
        });

		
		add(personsGrid);
	}
}
