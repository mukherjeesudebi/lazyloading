package com.example.application.views.grid;

import java.util.List;

import com.example.application.entities.Person;
import com.example.application.repositories.PersonRepository;
import com.example.application.views.MainLayout;
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
		List<Person> personsList = personRepository.findAll();
		Grid<Person> personsGrid = new Grid<>(Person.class, false);
		
		personsGrid.addColumn(Person::getFirstName).setHeader("First name");
		personsGrid.addColumn(Person::getLastName).setHeader("Last name");
		personsGrid.addColumn(Person::getEmail).setHeader("Email");
		personsGrid.addColumn(Person::getProfession).setHeader("Profession");

		personsGrid.setItems(personsList);
		
		add(personsGrid);
	}
}
