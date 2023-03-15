package com.example.application.views.grid;

import com.example.application.dto.PersonDTO;
import com.example.application.service.PersonService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@PageTitle("Grid")
@Route(value = "grid-view", layout = MainLayout.class)
@Component
public class GridView extends HorizontalLayout {

    private PersonService PersonService;

    public GridView(@Autowired PersonService personService) {
        Grid<PersonDTO> personsGrid = new Grid<>(PersonDTO.class, false);
        personsGrid.addColumn(PersonDTO::getFirstName).setHeader("First name");
        personsGrid.addColumn(PersonDTO::getLastName).setHeader("Last name");
        personsGrid.addColumn(PersonDTO::getEmail).setHeader("Email");
        personsGrid.addColumn(PersonDTO::getOccupation).setHeader("Occupation");
        personsGrid.addColumn(PersonDTO::getFavoriteFood).setHeader("Favorite food");
        personsGrid.setItems(personService::list);

        personsGrid.addSelectionListener(selection -> {
            Optional<PersonDTO> optionalPerson = selection.getFirstSelectedItem();
            if (optionalPerson.isPresent()) {
                String url = "/editPersonDetails/" + optionalPerson.get().getId();
                UI.getCurrent().navigate(url);
            }
        });
        add(personsGrid);
    }
}
