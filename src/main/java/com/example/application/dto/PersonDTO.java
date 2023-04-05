package com.example.application.dto;

public class PersonDTO {

    private Long id;
    private int consistencyVersion;
    private String firstName;
    private String lastName;
    private String email;
    private OccupationDTO occupation;
    private FoodDTO favoriteFood;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

	public OccupationDTO getOccupation() {
		return occupation;
	}

	public void setOccupation(OccupationDTO occupation) {
		this.occupation = occupation;
	}

	public FoodDTO getFavoriteFood() {
		return favoriteFood;
	}

	public void setFavoriteFood(FoodDTO favoriteFood) {
		this.favoriteFood = favoriteFood;
	}

	public int getConsistencyVersion() {
		return consistencyVersion;
	}

	public void setConsistencyVersion(int consistencyVersion) {
		this.consistencyVersion = consistencyVersion;
	}

}
