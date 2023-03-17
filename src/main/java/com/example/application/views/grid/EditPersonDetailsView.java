package com.example.application.views.grid;

import com.example.application.dto.FoodDTO;
import com.example.application.dto.OccupationDTO;
import com.example.application.dto.PersonDTO;
import com.example.application.service.FoodService;
import com.example.application.service.OccupationService;
import com.example.application.service.PersonService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Route("editPersonDetails")
public class EditPersonDetailsView extends VerticalLayout implements HasUrlParameter<String>, AfterNavigationObserver {

	private Long personId;
	private TextField firstName;
	private TextField lastName;
	private TextField email;
	private ComboBox<OccupationDTO> occupation;
	private ComboBox<FoodDTO> favoriteFood;
	private FormLayout formLayout;
	private PersonDTO selectedPerson;
	private Binder<PersonDTO> binder;

	private final PersonService personService;
	private final OccupationService occupationService;
	private final FoodService foodService;

	public EditPersonDetailsView(@Autowired PersonService personService,
								 @Autowired OccupationService occupationService,
								 @Autowired FoodService foodService) {
		firstName = new TextField("First name");
		lastName = new TextField("Last name");
		email = new TextField("Email id");
		occupation = new ComboBox<>("Occupation");
		favoriteFood = new ComboBox<>("Favorite food");

		formLayout = new FormLayout();
		formLayout.add(firstName, lastName, email, occupation, favoriteFood);
		formLayout.setResponsiveSteps(new ResponsiveStep("0", 2));
		formLayout.setColspan(email, 2);

		Button saveChangesButton = new Button("Save Changes");
		add(formLayout, saveChangesButton);

		saveChangesButton.addClickListener(event -> {
			try {
				binder.writeBean(selectedPerson);
				personService.save(selectedPerson);
			} catch (ValidationException e) {
				e.printStackTrace();
			}

		});

		this.personService = personService;
		this.occupationService = occupationService;
		this.foodService = foodService;
	}

	@Override
	public void setParameter(BeforeEvent event, String parameter) {
		setPersonId(Long.parseLong(parameter));
	}

	@Override
	public void afterNavigation(AfterNavigationEvent event) {
		Optional<PersonDTO> optional = personService.findById(getPersonId());
		this.populateComboBoxData();
		if (optional.isPresent()) {
			selectedPerson = optional.get();
			this.bindFormData();
			binder.readBean(selectedPerson);
		}

	}

	public void bindFormData() {
		binder = new Binder<>(PersonDTO.class);
		binder.forField(firstName).bind(PersonDTO::getFirstName, PersonDTO::setFirstName);
		binder.forField(lastName).bind(PersonDTO::getLastName, PersonDTO::setLastName);
		binder.forField(email).bind(PersonDTO::getEmail, PersonDTO::setEmail);
		binder.forField(occupation).bind(PersonDTO::getOccupation, PersonDTO::setOccupation);
		binder.forField(favoriteFood).bind(PersonDTO::getFavoriteFood, PersonDTO::setFavoriteFood);
	}

	public void populateComboBoxData() {
		occupation.setItems(occupationService::listBySingleFilter);
		favoriteFood.setItems(foodService::listBySingleFilter);
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

}
