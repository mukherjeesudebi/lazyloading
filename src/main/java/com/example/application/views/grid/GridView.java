package com.example.application.views.grid;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.application.dataproviders.PersonDataProvider;
import com.example.application.dto.PersonDTO;
import com.example.application.dto.PersonFilterDTO;
import com.example.application.service.PersonService;
import com.example.application.util.GridHeader;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Grid")
@Route(value = "grid-view", layout = MainLayout.class)
public class GridView extends HorizontalLayout {

	private PersonFilterDTO personFilter;
	private PersonDataProvider dataProvider;
	private ConfigurableFilterDataProvider<PersonDTO, Void, PersonFilterDTO> filterDataProvider;

	public GridView(@Autowired PersonService personService) {
		Grid<PersonDTO> personsGrid = new Grid<>(PersonDTO.class, false);

		personFilter = new PersonFilterDTO();
		dataProvider = new PersonDataProvider(personService);
		filterDataProvider = dataProvider.withConfigurableFilter();

		Grid.Column<PersonDTO> firstNameColumn = personsGrid.addColumn(PersonDTO::getFirstName);
		Grid.Column<PersonDTO> lastNameColumn = personsGrid.addColumn(PersonDTO::getLastName);
		Grid.Column<PersonDTO> emailColumn = personsGrid.addColumn(PersonDTO::getEmail);
		Grid.Column<PersonDTO> occupationColumn = personsGrid.addColumn(PersonDTO::getOccupation);
		Grid.Column<PersonDTO> favoriteFoodColumn = personsGrid.addColumn(PersonDTO::getFavoriteFood);

		personsGrid.setItems(filterDataProvider);

		personsGrid.getHeaderRows().clear();
		HeaderRow headerRow = personsGrid.appendHeaderRow();

		headerRow.getCell(firstNameColumn).setComponent(createFilterHeader(GridHeader.FIRSTNAME.label));
		headerRow.getCell(lastNameColumn).setComponent(createFilterHeader(GridHeader.LASTNAME.label));
		headerRow.getCell(emailColumn).setComponent(createFilterHeader(GridHeader.EMAIL.label));
		headerRow.getCell(occupationColumn).setComponent(createFilterHeader(GridHeader.OCCUPATION.label));
		headerRow.getCell(favoriteFoodColumn).setComponent(createFilterHeader(GridHeader.FAVORITEFOOD.label));

		personsGrid.addSelectionListener(selection -> {
			Optional<PersonDTO> optionalPerson = selection.getFirstSelectedItem();
			if (optionalPerson.isPresent()) {
				String url = "/editPersonDetails/" + optionalPerson.get().getId();
				UI.getCurrent().navigate(url);
			}
		});
		add(personsGrid);
	}

	private Component createFilterHeader(String labelText) {
		Label label = new Label(labelText);
		label.getStyle().set("padding-top", "var(--lumo-space-m)").set("font-size", "var(--lumo-font-size-xs)");
		TextField textField = new TextField();
		textField.setValueChangeMode(ValueChangeMode.EAGER);
		textField.setClearButtonVisible(true);
		textField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
		textField.setWidthFull();
		textField.getStyle().set("max-width", "100%");
		textField.addValueChangeListener(e -> {
			createValueChangeListener(e.getValue(), labelText);
		});
		VerticalLayout layout = new VerticalLayout(label, textField);
		layout.getThemeList().clear();
		layout.getThemeList().add("spacing-xs");

		return layout;
	}

	private void createValueChangeListener(String value, String labelText) {
		if (GridHeader.FIRSTNAME.label.equals(labelText)) {
			personFilter.setFirstName(value);
		} else if (GridHeader.LASTNAME.label.equals(labelText)) {
			personFilter.setLastName(value);
		} else if (GridHeader.EMAIL.label.equals(labelText)) {
			personFilter.setEmail(value);
		} else if (GridHeader.OCCUPATION.label.equals(labelText)) {
			personFilter.setOccupation(value);
		} else if (GridHeader.FAVORITEFOOD.label.equals(labelText)) {
			personFilter.setFavoriteFood(value);
		}

		filterDataProvider.setFilter(personFilter);

	}
}
