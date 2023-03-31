package com.example.application.util;

public enum GridSorterProperty {
	FIRSTNAME("firstName"), LASTNAME("lastName"), EMAIL("email"), OCCUPATION("occupation"),
	FAVORITEFOOD("favoriteFood");

	public final String label;

	private GridSorterProperty(String label) {
		this.label = label;
	}
}
