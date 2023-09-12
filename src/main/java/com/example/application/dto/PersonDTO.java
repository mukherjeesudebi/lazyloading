package com.example.application.dto;

import java.util.Objects;

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

	@Override
	public int hashCode() {
		return Objects.hash(consistencyVersion, email, favoriteFood, firstName, id, lastName, occupation);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PersonDTO other = (PersonDTO) obj;
		return consistencyVersion == other.consistencyVersion && Objects.equals(email, other.email)
				&& Objects.equals(favoriteFood, other.favoriteFood) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(id, other.id) && Objects.equals(lastName, other.lastName)
				&& Objects.equals(occupation, other.occupation);
	}

	
}
