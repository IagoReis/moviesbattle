package br.com.letscode.moviesbattle.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.letscode.moviesbattle.business.BattleBusiness;
import br.com.letscode.moviesbattle.data.entity.BattleEntity;
import br.com.letscode.moviesbattle.dto.QuizDto;
import br.com.letscode.moviesbattle.dto.RoundDto;
import br.com.letscode.moviesbattle.enums.BattleStatusEnum;
import br.com.letscode.moviesbattle.enums.RoundStatusEnum;
import br.com.letscode.moviesbattle.exception.UnavailableQuizException;
import br.com.letscode.moviesbattle.exception.UnavailableRoundException;
import br.com.letscode.moviesbattle.exception.WrongMovieException;
import br.com.letscode.moviesbattle.mapper.dto.QuizMapper;
import br.com.letscode.moviesbattle.mapper.dto.RoundMapper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/quiz")
@SecurityRequirement(name = "moviesbattleapi")
public class BattleController {
	
	@Autowired
	private BattleBusiness business;
	
	@PostMapping("/iniciar")
	public ResponseEntity<QuizDto> start() {
				
		final var battle = business.insert();
		
		final var dto = QuizMapper.INSTANCE.map(battle);
		
		final var response = new ResponseEntity<>(dto, HttpStatus.OK);
		
		return response;		
	}
	
	@PostMapping("/{idQuiz}/encerrar")
	public ResponseEntity<QuizDto> end(@PathVariable(name = "idQuiz") final Long idBattle) {
		
		final var battle = business.end(idBattle);
		
		final var dto = QuizMapper.INSTANCE.map(battle);
		
		final var response = new ResponseEntity<>(dto, HttpStatus.OK);
		
		return response;		
	}
	
	@GetMapping("/{idQuiz}")
	public ResponseEntity<RoundDto> quiz(@PathVariable(name = "idQuiz") final Long idBattle) {
				
		final var battle = business.getById(idBattle);
		
		if(!battle.getStatus().equals(BattleStatusEnum.WAITING)) {
			throw new UnavailableQuizException();
		}
		
		final var rounds = battle.getRounds().stream().filter(a -> a.getStatus().equals(RoundStatusEnum.WAITING)).collect(Collectors.toList());
		
		if (rounds.size() != 1) {
			final var round = business.addRound(idBattle);
			rounds.add(round);
		}
		
		final var dto = RoundMapper.INSTANCE.map(rounds.get(0));
		
		final var response = new ResponseEntity<>(dto, HttpStatus.OK);
		
		return response;		
	}
	
	@GetMapping("/ranking")
	public ResponseEntity<?> ranking() {
		
		final List<BattleEntity> ranking = business.getRanking();
		
		final var response = new ResponseEntity<>(ranking, HttpStatus.OK);
		
		return response;		
	}
	
	@PostMapping("/{idQuiz}/rodada/{idRodada}/filme/{idFilme}")
	public ResponseEntity<?> start(
			@PathVariable(name = "idQuiz") final Long idBattle,
			@PathVariable(name = "idRodada") Long idRound, 
			@PathVariable(name = "idFilme") String idMovie) {
		
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
		
		if(correct) {
			battle.setPoints(battle.getPoints()+1);
		}
		else {
			battle.setMistakes(battle.getMistakes()+1);
		}
		
		round.setCorrect(correct);
		round.setStatus(RoundStatusEnum.ANSWERED);
		
		business.updateRound(round);
		
		business.updateValues(battle.getRounds().size(), battle.getMistakes(), battle.getPoints(), battle.getId());
		
		if(battle.getMistakes() >= 3) {
			business.end(idBattle);
		}
		
		final var response = new ResponseEntity<>(correct, HttpStatus.OK);
		
		return response;		
	}

}
