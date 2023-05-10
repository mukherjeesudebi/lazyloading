package com.example.application.dto;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.data.provider.SortDirection;

public class PersonRestClientDTO {
	private Integer pageSize;
	private Integer pageNumber;
	private PersonFilterDTO personFilter;
	private String sortKey;
	private SortDirection direction;

	public PersonRestClientDTO() {
	}

	public PersonRestClientDTO(PageRequest filterPageRequest) {
		this.pageSize = filterPageRequest.getPageSize();
		this.pageNumber = filterPageRequest.getPageNumber();
	}

	public PersonRestClientDTO(PageRequest filterPageRequest, PersonFilterDTO personFilter, String sortKey,
			SortDirection direction) {
		this.pageSize = filterPageRequest.getPageSize();
		this.pageNumber = filterPageRequest.getPageNumber();
		this.personFilter = personFilter;
		this.sortKey = sortKey;
		this.direction = direction;
	}

	public Pageable toPageable() {
		Pageable pageable = Pageable.ofSize(pageSize);
		if (pageNumber != null) {
			pageable.withPage(pageNumber);
		}
		return pageable;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public PersonFilterDTO getPersonFilter() {
		return personFilter;
	}

	public void setPersonFilter(PersonFilterDTO personFilter) {
		this.personFilter = personFilter;
	}

	public String getSortKey() {
		return sortKey;
	}

	public void setSortKey(String sortKey) {
		this.sortKey = sortKey;
	}

	public SortDirection getDirection() {
		return direction;
	}

	public void setDirection(SortDirection direction) {
		this.direction = direction;
	}

}
