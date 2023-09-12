package com.example.application.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.application.dto.PersonDTO;
import com.example.application.dto.PersonFilterDTO;
import com.example.application.dto.PersonRestClientDTO;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.SortDirection;

@Service
public class PersonService implements DataService<PersonDTO, PersonFilterDTO> {

	//@Value("${server.port}")
	private String serverPort = "8080";

	//@Value("${vaadin.domainName}")
	private String domainName = "http://localhost:";

	private static final Logger LOGGER = LoggerFactory.getLogger(PersonService.class);
	
	public List<PersonDTO> listAll(){
		final String url = String.format(domainName + serverPort + "/listall");


		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List<PersonDTO>> responseEntity = restTemplate.exchange(url, HttpMethod.POST, null,
				new ParameterizedTypeReference<List<PersonDTO>>() {
				});
		return responseEntity.getBody();
	}

	@Override
	public Stream<PersonDTO> list(Query<PersonDTO, Void> query) {
		LOGGER.info("Fetching page: {}, PageSize: {}", query.getPage(), query.getPageSize());
		final String url = String.format(domainName + serverPort + "/list", query.getPageSize(), query.getPage());

		PersonRestClientDTO personRestClientDTO = new PersonRestClientDTO(
				PageRequest.of(query.getPage(), query.getPageSize()));

		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<PersonRestClientDTO> request = new HttpEntity<>(personRestClientDTO);
		ResponseEntity<List<PersonDTO>> responseEntity = restTemplate.exchange(url, HttpMethod.POST, request,
				new ParameterizedTypeReference<List<PersonDTO>>() {
				});
		return responseEntity.getBody().stream();
	}

	@Override
	public Stream<PersonDTO> findAllByFilter(PersonFilterDTO personFilter, PageRequest pageRequest,
			GridSortOrder<PersonDTO> sortOrder) {
		String sortKey = null;
		SortDirection direction = null;
		if (sortOrder != null) {
			sortKey = sortOrder.getSorted().getKey();
			direction = sortOrder.getDirection();
		}
		PersonRestClientDTO personRestClientDTO = new PersonRestClientDTO(pageRequest, personFilter, sortKey,
				direction);
		final String url = String.format(domainName + serverPort + "/findAllByFilter");
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<PersonRestClientDTO> request = new HttpEntity<>(personRestClientDTO);
		ResponseEntity<List<PersonDTO>> responseEntity = restTemplate.exchange(url, HttpMethod.POST, request,
				new ParameterizedTypeReference<List<PersonDTO>>() {
				});
		return responseEntity.getBody().stream();
	}

	@Override
	public Optional<PersonDTO> findById(Long personId) {
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<Long> request = new HttpEntity<>(personId);
		final String url = String.format(domainName + serverPort + "/findById");
		ResponseEntity<Optional<PersonDTO>> responseEntity = restTemplate.exchange(url, HttpMethod.POST, request,
				new ParameterizedTypeReference<Optional<PersonDTO>>() {
				});
		return responseEntity.getBody();
	}

	@Override
	public PersonDTO save(PersonDTO dto) {
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<PersonDTO> request = new HttpEntity<>(dto);
		final String url = String.format(domainName + serverPort + "/save");
		ResponseEntity<PersonDTO> responseEntity = restTemplate.exchange(url, HttpMethod.POST, request,
				new ParameterizedTypeReference<PersonDTO>() {
				});
		return responseEntity.getBody();
	}

	@Override
	public Stream<PersonDTO> listBySingleFilter(Query<PersonDTO, String> query) {
		throw new UnsupportedOperationException("Not implemented");
	}
}
