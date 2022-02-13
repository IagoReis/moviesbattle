package br.com.letscode.moviesbattle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.letscode.moviesbattle.business.BattleBusiness;
import br.com.letscode.moviesbattle.data.entity.BattleEntity;
import br.com.letscode.moviesbattle.repository.UserRepository;

@Controller
@RequestMapping("/battles")
public class BattleContorller {
	
	@Autowired
	private BattleBusiness business;
	
	@Autowired
	private UserRepository repository;
	
	@PostMapping("/start")
	public ResponseEntity<BattleEntity> start() {
		
		final var user = repository.findAll().get(0);
				
		final var battle = business.start(user);
		
		final var response = new ResponseEntity<>(battle, HttpStatus.OK);
		
		return response;		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<BattleEntity> getById(@PathVariable final Long id) {
				
		final var battle = business.getById(id);
		
		final var response = new ResponseEntity<>(battle, HttpStatus.OK);
		
		return response;		
	}
	
	@GetMapping("/{id}/answer")
	public ResponseEntity<BattleEntity> start(@PathVariable final Long id) {
				
		final var battle = business.getById(id);
		
		final var response = new ResponseEntity<>(battle, HttpStatus.OK);
		
		return response;		
	}

}
