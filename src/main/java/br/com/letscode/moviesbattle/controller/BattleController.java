package br.com.letscode.moviesbattle.controller;

import java.util.stream.Collectors;

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
import br.com.letscode.moviesbattle.enums.BattleStatusEnum;
import br.com.letscode.moviesbattle.enums.RoundStatusEnum;
import br.com.letscode.moviesbattle.repository.UserRepository;

@Controller
@RequestMapping("/battles")
public class BattleController {
	
	@Autowired
	private BattleBusiness business;
	
	@Autowired
	private UserRepository repository;
	
	@PostMapping("/start")
	public ResponseEntity<BattleEntity> start() {
		
		final var user = repository.findAll().get(0);
				
		final var battle = business.insert(user);
		
		final var response = new ResponseEntity<>(battle, HttpStatus.OK);
		
		return response;		
	}
	
	@PostMapping("/{idBattle}/end")
	public ResponseEntity<BattleEntity> end(@PathVariable final Long idBattle) {
		
		final var battle = business.end(idBattle, BattleStatusEnum.CANCELLED);
		
		final var response = new ResponseEntity<>(battle, HttpStatus.OK);
		
		return response;		
	}
	
	@GetMapping("/{idBattle}")
	public ResponseEntity<BattleEntity> getById(@PathVariable final Long idBattle) {
				
		final var battle = business.getById(idBattle);
		
		final var response = new ResponseEntity<>(battle, HttpStatus.OK);
		
		return response;		
	}
	
	@GetMapping("/{idBattle}/quiz")
	public ResponseEntity<?> start(@PathVariable final Long idBattle) {
				
		final var battle = business.getById(idBattle);
		
		final var rounds = battle.getRounds().stream().filter(a -> a.getStatus().equals(RoundStatusEnum.WAITING)).collect(Collectors.toList());
		
		if (rounds.size() != 1) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		
		final var response = new ResponseEntity<>(rounds.get(0), HttpStatus.OK);
		
		return response;		
	}
	
	@PostMapping("/{idBattle}/quiz/{idRound}/{idMovie}")
	public ResponseEntity<?> start(@PathVariable final Long idBattle, @PathVariable Long idRound, @PathVariable String idMovie) {
		
		final var battle = business.getById(idBattle);
		
		final var rounds = battle.getRounds().stream().filter(r -> r.getId().equals(idRound)).collect(Collectors.toList());
		
		if(rounds.size() != 1) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		
		final var round = rounds.get(0);		
		final var movies = round.getMovies();
		final var selectedMovies = movies.stream().filter(m -> m.getId().equals(idMovie)).collect(Collectors.toList());
		final var notSelectedMovies = movies.stream().filter(m -> !m.getId().equals(idMovie)).collect(Collectors.toList());
		
		if(!round.getStatus().equals(RoundStatusEnum.WAITING) || selectedMovies.size() != 1 || notSelectedMovies.size() != 1 ) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		
		final var selectedMovie = selectedMovies.get(0);
		final var notSelectedMovie = notSelectedMovies.get(0);
		
		final var correct = selectedMovie.getRating().compareTo(notSelectedMovie.getRating()) > 0;
		
		round.setCorrect(correct);
		round.setStatus(RoundStatusEnum.ANSWERED);
		
		business.updateRound(round);
		
		if (battle.getRounds().size() < 4) {
			business.addRound(idBattle);
		}
		else {
			business.end(idBattle, BattleStatusEnum.ANSWERED);
		}

		final var response = new ResponseEntity<>(correct, HttpStatus.OK);
		
		return response;		
	}

}
