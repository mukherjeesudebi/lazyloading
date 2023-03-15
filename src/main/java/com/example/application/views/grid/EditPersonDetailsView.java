package com.example.application.views.grid;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;

import com.example.application.entities.Food;
import com.example.application.entities.Occupation;
import com.example.application.entities.Person;
import com.example.application.repositories.FoodRepository;
import com.example.application.repositories.OccupationRepository;
import com.example.application.repositories.PersonRepository;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.BackEndDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;

@Route("editPersonDetails")
public class EditPersonDetailsView extends VerticalLayout implements HasUrlParameter<String>, AfterNavigationObserver {

	private Long personId;
	private TextField firstName;
	private TextField lastName;
	private TextField email;
	private ComboBox<Occupation> occupation;
	private ComboBox<Food> favoriteFood;
	private FormLayout formLayout;
	private Person selectedPerson;
	private Binder<Person> binder;

	private PersonRepository personRepository;
	private OccupationRepository occupationRepository;
	private FoodRepository foodRepository;

	public EditPersonDetailsView(PersonRepository personRepository, OccupationRepository occupationRepository,
			FoodRepository foodRepository) {
		this.personRepository = personRepository;
		this.occupationRepository = occupationRepository;
		this.foodRepository = foodRepository;

		firstName = new TextField("First name");
		lastName = new TextField("Last name");
		email = new TextField("Email id");
		occupation = new ComboBox<>("Occupation");
		favoriteFood = new ComboBox<>("Favorite food");

		formLayout = new FormLayout();
		formLayout.add(firstName, lastName, email, occupation, favoriteFood);
		formLayout.setResponsiveSteps(new ResponsiveStep("0", 2));
		formLayout.setColspan(email, 2);
		add(formLayout);

	}

	@Override
	public void setParameter(BeforeEvent event, String parameter) {
		setPersonId(Long.parseLong(parameter));
	}

	@Override
	public void afterNavigation(AfterNavigationEvent event) {
		Optional<Person> optional = personRepository.findById(getPersonId());
		this.populateComboBoxData();
		if (optional.isPresent()) {
			selectedPerson = optional.get();
			this.bindFormData();
			binder.readBean(selectedPerson);
		}

	}

	public void bindFormData() {
		binder = new Binder<>(Person.class);
		binder.forField(firstName).bind(Person::getFirstName, Person::setFirstName);
		binder.forField(lastName).bind(Person::getLastName, Person::setLastName);
		binder.forField(email).bind(Person::getEmail, Person::setEmail);
		binder.forField(occupation).bind(Person::getOccupation, Person::setOccupation);
		binder.forField(favoriteFood).bind(Person::getFavoriteFood, Person::setFavoriteFood);
	}

	public void populateComboBoxData() {
		occupation.setItems(query -> {
			return occupationRepository.findAll(
					PageRequest.of(query.getPage(), query.getPageSize())
					).stream();
		});
		favoriteFood.setItems(query -> {
			return foodRepository.findAll(
					PageRequest.of(query.getPage(), query.getPageSize())
					).stream();
		});
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

}
