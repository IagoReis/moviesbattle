package br.com.letscode.moviesbattle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.letscode.moviesbattle.dto.MovieDto;
import br.com.letscode.moviesbattle.integration.OmdbIntegration;

@Controller
@RequestMapping("/movies")
public class MovieController {
	
	@Autowired
	private OmdbIntegration omdbIntegration;

	@GetMapping("/{id}")
	public ResponseEntity<MovieDto> getById(@PathVariable final String id) {
		
		final MovieDto movie = omdbIntegration.getById(id);
		
		final var response = new ResponseEntity<>(movie, HttpStatus.OK);
		
		return response;
	}
	
}
