package com.example.application.dto;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.data.provider.SortDirection;

public class PageableRestClientDTO {
	private Integer pageSize;
	private Integer pageNumber;
	private String filterName;

	public PageableRestClientDTO() {
	}

	public PageableRestClientDTO(PageRequest filterPageRequest) {
		this.pageSize = filterPageRequest.getPageSize();
		this.pageNumber = filterPageRequest.getPageNumber();
	}

	public PageableRestClientDTO(PageRequest filterPageRequest,String filterName) {
		this.pageSize = filterPageRequest.getPageSize();
		this.pageNumber = filterPageRequest.getPageNumber();
		this.filterName  = filterName;
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

	public String getFilterName() {
		return filterName;
	}

	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}
	
}
