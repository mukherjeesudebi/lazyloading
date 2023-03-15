package com.example.application.entities;

import javax.persistence.Entity;

@Entity
public class Food extends AbstractEntity {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
    public String toString() {
        return name;
    }
}
