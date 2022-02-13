package br.com.letscode.moviesbattle.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import br.com.letscode.moviesbattle.configuration.properties.OmdbProperties;
import br.com.letscode.moviesbattle.dto.MovieDto;


@Component
public class OmdbIntegration {
	
	@Autowired
	private OmdbProperties properties;	
	
	@Qualifier("OmdbRestTemplate")
	@Autowired
	private RestTemplate restTemplate;
	
	public MovieDto getById(final String id) {
		
		final var url = properties.getEndpoint().concat("?i=").concat(id);
		
		final ResponseEntity<MovieDto> responseEntity = restTemplate.getForEntity(url, MovieDto.class);
		
		final MovieDto response = responseEntity.getBody();
		
		return response;
	}	
	
}
