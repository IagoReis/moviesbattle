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
import br.com.letscode.moviesbattle.data.entity.UserEntity;
import br.com.letscode.moviesbattle.enums.BattleStatusEnum;
import br.com.letscode.moviesbattle.enums.RoundStatusEnum;
import br.com.letscode.moviesbattle.exception.UnavailableMoviesException;
import br.com.letscode.moviesbattle.repository.BattleRepository;
import br.com.letscode.moviesbattle.repository.MovieRepository;
import br.com.letscode.moviesbattle.repository.RoundRepository;

@Service
public class BattleBusiness {

	@Autowired
	private BattleRepository battleRepository;

	@Autowired
	private RoundRepository roundRepository;

	@Autowired
	private MovieRepository movieRepository;

	public BattleEntity insert(final UserEntity user) {

		final var battle = new BattleEntity();		
		battle.setUser(user);

		final var battleSaved = battleRepository.save(battle);
		
		this.addRound(battleSaved.getId());
				
		return battleSaved;
	}
	
	public BattleEntity end(final Long idBattle, final BattleStatusEnum status) {

		final var battle = this.getById(idBattle);
		battle.setStatus(status);
		
		Integer total = 0;
		Integer points = 0;		
		
		for(final RoundEntity round : battle.getRounds()) {
			
			total++;
			
			if(Boolean.TRUE.equals(round.getCorrect())) {
				points++;
			}
			
			if(round.getStatus().equals(RoundStatusEnum.WAITING)) {
				round.setStatus(RoundStatusEnum.CANCELLED);
				round.setCorrect(Boolean.FALSE);
				this.updateRound(round);
			}
		}
		
		var percent = new BigDecimal("100").divide(BigDecimal.valueOf(total)).multiply(BigDecimal.valueOf(points));
		percent = percent.setScale(2, RoundingMode.HALF_UP);
		
		battle.setPoints(points);
		battle.setPercent(percent);

		battleRepository.save(battle);
		
		return this.getById(idBattle);
	}

	public BattleEntity getById(final Long idBattle) {
		
		final var response = battleRepository.findById(idBattle).get();
		response.setRounds(roundRepository.findByBattleId(idBattle));
		
		return response;
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
		
		return roundSaved;
	}
	
	
		
}
