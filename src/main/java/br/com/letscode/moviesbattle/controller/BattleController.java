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
import br.com.letscode.moviesbattle.data.entity.RoundEntity;
import br.com.letscode.moviesbattle.dto.QuizDto;
import br.com.letscode.moviesbattle.dto.RoundDto;
import br.com.letscode.moviesbattle.enums.BattleStatusEnum;
import br.com.letscode.moviesbattle.enums.RoundStatusEnum;
import br.com.letscode.moviesbattle.exception.UnavailableQuizException;
import br.com.letscode.moviesbattle.exception.UnavailableRoundException;
import br.com.letscode.moviesbattle.exception.WrongMovieException;
import br.com.letscode.moviesbattle.mapper.dto.QuizMapper;
import br.com.letscode.moviesbattle.mapper.dto.RoundMapper;

@Controller
@RequestMapping("/quiz")
public class BattleController {
	
	@Autowired
	private BattleBusiness business;
	
	@PostMapping("/start")
	public ResponseEntity<QuizDto> start() {
				
		final var battle = business.insert();
		
		final var dto = QuizMapper.INSTANCE.map(battle);
		
		final var response = new ResponseEntity<>(dto, HttpStatus.OK);
		
		return response;		
	}
	
	@PostMapping("/{idBattle}/end")
	public ResponseEntity<QuizDto> end(@PathVariable final Long idBattle) {
		
		final var battle = business.end(idBattle, BattleStatusEnum.CANCELLED);
		
		final var dto = QuizMapper.INSTANCE.map(battle);
		
		final var response = new ResponseEntity<>(dto, HttpStatus.OK);
		
		return response;		
	}
	
	@GetMapping("/{idBattle}")
	public ResponseEntity<RoundDto> quiz(@PathVariable final Long idBattle) {
				
		final var battle = business.getById(idBattle);
		
		final var rounds = battle.getRounds().stream().filter(a -> a.getStatus().equals(RoundStatusEnum.WAITING)).collect(Collectors.toList());
		
		if (rounds.size() != 1) {
			final var round = business.addRound(idBattle);
			rounds.add(round);
		}
		
		final var dto = RoundMapper.INSTANCE.map(rounds.get(0));
		
		final var response = new ResponseEntity<>(dto, HttpStatus.OK);
		
		return response;		
	}
	
	@PostMapping("/{idBattle}/rodada/{idRound}/filme/{idMovie}")
	public ResponseEntity<?> start(@PathVariable final Long idBattle, @PathVariable Long idRound, @PathVariable String idMovie) {
		
		final var battle = business.getById(idBattle);
		
		final var rounds = battle.getRounds().stream().filter(r -> r.getId().equals(idRound)).collect(Collectors.toList());
		
		if(rounds.size() != 1 || !rounds.get(0).getStatus().equals(RoundStatusEnum.WAITING)) {
			throw new UnavailableRoundException();
		}
		
		final var round = rounds.get(0);		
		final var movies = round.getMovies();
		final var selectedMovies = movies.stream().filter(m -> m.getId().equals(idMovie)).collect(Collectors.toList());
		final var notSelectedMovies = movies.stream().filter(m -> !m.getId().equals(idMovie)).collect(Collectors.toList());
		
		if(selectedMovies.size() != 1 || notSelectedMovies.size() != 1 ) {
			throw new WrongMovieException();
		}
		
		final var selectedMovie = selectedMovies.get(0);
		final var notSelectedMovie = notSelectedMovies.get(0);
		
		final var correct = selectedMovie.getRating().compareTo(notSelectedMovie.getRating()) > 0;
		
		round.setCorrect(correct);
		round.setStatus(RoundStatusEnum.ANSWERED);
		
		business.updateRound(round);
		
		final var response = new ResponseEntity<>(correct, HttpStatus.OK);
		
		return response;		
	}

}
