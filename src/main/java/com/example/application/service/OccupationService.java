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

import com.example.application.dto.OccupationDTO;
import com.example.application.dto.OccupationFilterDTO;
import com.example.application.dto.PageableRestClientDTO;
import com.example.application.dto.PersonDTO;
import com.example.application.dto.PersonFilterDTO;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.data.provider.Query;

@Service
public class OccupationService implements DataService<OccupationDTO, OccupationFilterDTO> {

	@Value("${server.port}")
	private String serverPort;

	@Value("${vaadin.domainName}")
	private String domainName;

	private static final Logger LOGGER = LoggerFactory.getLogger(PersonService.class);

	@Override
	public Stream<OccupationDTO> list(Query<OccupationDTO, Void> query) {
		LOGGER.info("Fetching page: {}, PageSize: {}", query.getPage(), query.getPageSize());
		final String url = String.format(domainName + serverPort + "/occupationList");

		PageableRestClientDTO pageableRestClientDTO = new PageableRestClientDTO(
				PageRequest.of(query.getPage(), query.getPageSize()));

		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<PageableRestClientDTO> request = new HttpEntity<>(pageableRestClientDTO);
		ResponseEntity<List<OccupationDTO>> responseEntity = restTemplate.exchange(url, HttpMethod.POST, request,
				new ParameterizedTypeReference<List<OccupationDTO>>() {
				});
		return responseEntity.getBody().stream();
	}

	@Override
	public Optional<OccupationDTO> findById(Long occupationId) {
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<Long> request = new HttpEntity<>(occupationId);
		final String url = String.format(domainName + serverPort + "/findOccupationById");
		ResponseEntity<Optional<OccupationDTO>> responseEntity = restTemplate.exchange(url, HttpMethod.POST, request,
				new ParameterizedTypeReference<Optional<OccupationDTO>>() {
				});
		return responseEntity.getBody();
	}

	@Override
	public OccupationDTO save(OccupationDTO dto) {
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<OccupationDTO> request = new HttpEntity<>(dto);
		final String url = String.format(domainName + serverPort + "/saveOccupation");
		ResponseEntity<OccupationDTO> responseEntity = restTemplate.exchange(url, HttpMethod.POST, request,
				new ParameterizedTypeReference<OccupationDTO>() {
				});
		return responseEntity.getBody();
	}

	@Override
	public Stream<OccupationDTO> listBySingleFilter(Query<OccupationDTO, String> query) {
		if (hasNameFilter(query.getFilter())) {
			LOGGER.info("Fetching page: {}, PageSize: {}, Filter: {}", query.getPage(), query.getPageSize(),
					query.getFilter());
			final String url = String.format(domainName + serverPort + "/findAllByOccNameContainingIgnoreCase");

			PageableRestClientDTO pageableRestClientDTO = new PageableRestClientDTO(
					PageRequest.of(query.getPage(), query.getPageSize()), query.getFilter().get());
			RestTemplate restTemplate = new RestTemplate();
			HttpEntity<PageableRestClientDTO> request = new HttpEntity<>(pageableRestClientDTO);
			ResponseEntity<List<OccupationDTO>> responseEntity = restTemplate.exchange(url, HttpMethod.POST, request,
					new ParameterizedTypeReference<List<OccupationDTO>>() {
					});
			return responseEntity.getBody().stream();
		} else {
			LOGGER.info("Fetching page: {}, PageSize: {}", query.getPage(), query.getPageSize());
			final String url = String.format(domainName + serverPort + "/occupationList");
			PageableRestClientDTO pageableRestClientDTO = new PageableRestClientDTO(
					PageRequest.of(query.getPage(), query.getPageSize()));
			RestTemplate restTemplate = new RestTemplate();
			HttpEntity<PageableRestClientDTO> request = new HttpEntity<>(pageableRestClientDTO);
			ResponseEntity<List<OccupationDTO>> responseEntity = restTemplate.exchange(url, HttpMethod.POST, request,
					new ParameterizedTypeReference<List<OccupationDTO>>() {
					});
			return responseEntity.getBody().stream();
		}
	}

	private boolean hasNameFilter(Optional<String> filter) {
		return filter.filter(s -> !s.isEmpty()).isPresent();
	}

	@Override
	public Stream<PersonDTO> findAllByFilter(PersonFilterDTO personFilter, PageRequest pageRequest,
			GridSortOrder<PersonDTO> sortOrder) {
		throw new UnsupportedOperationException("Not implemented");
	}

}
