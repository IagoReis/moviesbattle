package br.com.letscode.moviesbattle.business;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.letscode.moviesbattle.data.entity.BattleEntity;
import br.com.letscode.moviesbattle.data.entity.MovieEntity;
import br.com.letscode.moviesbattle.data.entity.RoundEntity;
import br.com.letscode.moviesbattle.enums.BattleStatusEnum;
import br.com.letscode.moviesbattle.enums.RoundStatusEnum;
import br.com.letscode.moviesbattle.exception.UnavailableMoviesException;
import br.com.letscode.moviesbattle.exception.UnavailableQuizException;
import br.com.letscode.moviesbattle.exception.UserNotAllowedException;
import br.com.letscode.moviesbattle.repository.BattleRepository;
import br.com.letscode.moviesbattle.repository.MovieRepository;
import br.com.letscode.moviesbattle.repository.RoundRepository;
import br.com.letscode.moviesbattle.security.AuthFacade;

@Service
public class BattleBusiness {

	@Autowired
	private BattleRepository battleRepository;

	@Autowired
	private RoundRepository roundRepository;

	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private AuthFacade authFacade; 
	
	private void validateUser(final BattleEntity battle) {
		if(!battle.getUser().getId().equals(authFacade.getUser().getId())) {
			throw new UserNotAllowedException();
		}
	}

	public void updateValues(final Integer total, final Integer mistakes, final Integer points, final Long id) {
		
		final var battle = battleRepository.getById(id);
		
		battle.setTotal(total);
		battle.setMistakes(mistakes);
		battle.setPoints(points);
		
		battleRepository.save(battle);
	}
	
	public List<BattleEntity> getRanking() {
		
		final var ranking = battleRepository.getRanking();
		
		return ranking;
	}
	
	public BattleEntity insert() {
		
		final var user = authFacade.getUser();

		final var battle = new BattleEntity();
		battle.setUser(user);

		final var battleSaved = battleRepository.save(battle);
				
		return battleSaved;
	}
	
	public BattleEntity end(final Long idBattle) {

		var battle = this.getById(idBattle);
		
		if(!battle.getStatus().equals(BattleStatusEnum.WAITING)) {
			throw new UnavailableQuizException();
		}
		
		validateUser(battle);
		
		battle.setStatus(BattleStatusEnum.ANSWERED);
		
		Integer total = 0;
		Integer points = 0;
		Integer mistakes = 0;
		
		for(final RoundEntity round : battle.getRounds()) {
			
			total++;
			
			if(Boolean.TRUE.equals(round.getCorrect())) {
				points++;
			}
			else {
				mistakes++;
			}
			
			if(round.getStatus().equals(RoundStatusEnum.WAITING)) {
				round.setStatus(RoundStatusEnum.CANCELLED);
				round.setCorrect(Boolean.FALSE);
				this.updateRound(round);
			}
		}
		
		var percent = new BigDecimal("0.00").setScale(2, RoundingMode.HALF_UP);
		
		if(total > 0) {
			percent = new BigDecimal("100")
					.divide(BigDecimal.valueOf(total), 2, RoundingMode.HALF_UP)
					.multiply(BigDecimal.valueOf(points));
			percent = percent.setScale(2, RoundingMode.HALF_UP);
		}
		
		battle.setPoints(points);
		battle.setTotal(total);
		battle.setMistakes(mistakes);
		battle.setPercent(percent);

		battleRepository.save(battle);
		
		return this.getById(idBattle);
	}

	public BattleEntity getById(final Long idBattle) {
		
		final var battle = battleRepository.findById(idBattle).get();
		
		validateUser(battle);
		
		return battle;
	}
	
	public RoundEntity updateRound(final RoundEntity roundEntity) {
		return roundRepository.save(roundEntity);
	}
	
	public List<RoundEntity> getRounds(final Long idBattle) {
		
		final var response = roundRepository.findByBattleId(idBattle);
		
		return response;
	}
	
	public RoundEntity addRound(final Long idBattle) {
		
		final List<String> unavailableFirst = new ArrayList<>();
		unavailableFirst.add("default-init");
		
		List<String> unavailableSecond;
		
		final var movies = new ArrayList<MovieEntity>();
		
		Optional<MovieEntity> firstMovieOptional; 
		Optional<MovieEntity> secondMovieOptional;
		
		final var rounds = this.getRounds(idBattle);
				
		do {			
			firstMovieOptional = movieRepository.findAvailable(unavailableFirst);
			
			if (firstMovieOptional.isEmpty()) {
				throw new UnavailableMoviesException("Não há filmes disponíveis"); 
			}
			
			unavailableFirst.add(firstMovieOptional.get().getId());
			
			unavailableSecond = new ArrayList<>();
			unavailableSecond.add(firstMovieOptional.get().getId());
			
			if(!Objects.isNull(rounds)) {
				for(final RoundEntity round : rounds) {
					if (round.getMovies().get(0).getId().equals(firstMovieOptional.get().getId())) {
						unavailableSecond.add(round.getMovies().get(1).getId());
					}
					
					if (round.getMovies().get(1).getId().equals(firstMovieOptional.get().getId())) {
						unavailableSecond.add(round.getMovies().get(0).getId());
					}
				}
			}
			
			secondMovieOptional = movieRepository.findAvailable(unavailableSecond);
			
			if(
				secondMovieOptional.isEmpty() ||
				(firstMovieOptional.get().getRating().compareTo(secondMovieOptional.get().getRating()) == 0)
			) {
				continue;
			}
			
			movies.add(firstMovieOptional.get());
			movies.add(secondMovieOptional.get());
		}
		while(movies.isEmpty());
		
		final var round = new RoundEntity();
		round.setBattle(BattleEntity.builder().id(idBattle).build());
		round.setMovies(movies);
		round.setNumber(rounds.size() + 1);
		
		final RoundEntity roundSaved = roundRepository.save(round);
		
		return roundRepository.findById(roundSaved.getId()).get();
	}
	
	
		
}
