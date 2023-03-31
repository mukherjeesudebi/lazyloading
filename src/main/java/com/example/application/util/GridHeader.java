package com.example.application.util;

public enum GridHeader {
	FIRSTNAME("First Name"), LASTNAME("Last Name"), EMAIL("Email"), OCCUPATION("Occupation"),
	FAVORITEFOOD("Favorite Food");

	public final String label;

	private GridHeader(String label) {
		this.label = label;
	}
}
