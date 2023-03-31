package com.example.application.dto;

public class PersonFilterDTO {

	private String firstName;
	private String lastName;
	private String email;
	private String occupation;
	private String favoriteFood;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getFavoriteFood() {
		return favoriteFood;
	}

	public void setFavoriteFood(String favoriteFood) {
		this.favoriteFood = favoriteFood;
	}
	
	public boolean test(PersonDTO personDTO) {
        boolean matchesFirstName = matches(personDTO.getFirstName(), firstName);
        boolean matchesLastName = matches(personDTO.getLastName(), lastName);
        boolean matchesEmail = matches(personDTO.getEmail(), email);
        boolean matchesOccupation = matches(personDTO.getOccupation()!= null? personDTO.getOccupation().toString(): "", occupation);
        boolean matchesFavoriteFood = matches(personDTO.getFavoriteFood()!= null? personDTO.getFavoriteFood().toString(): "" , favoriteFood);
       
        return matchesFirstName && matchesLastName && matchesEmail && matchesOccupation && matchesFavoriteFood;
        
        //return matchesEmail;
    }

    private boolean matches(String value, String searchTerm) {
        return searchTerm == null || searchTerm.isEmpty()
                || value.toLowerCase().contains(searchTerm.toLowerCase());
    }

}
