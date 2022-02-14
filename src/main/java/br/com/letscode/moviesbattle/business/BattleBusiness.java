package br.com.letscode.moviesbattle.business;

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
		
		return this.getById(battleSaved.getId());
	}

	public BattleEntity getById(final Long idBattle) {
		
		final var response = battleRepository.findById(idBattle).get();
		response.setRounds(roundRepository.findByBattleId(idBattle));
		
		return response;
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
				throw new UnavailableMoviesException("PRIMEIRO FILME NÃO DISPONÍVEL"); 
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
			
			if(secondMovieOptional.isEmpty()) {
				continue;
			}
			
			movies.add(firstMovieOptional.get());
			movies.add(secondMovieOptional.get());
		}
		while(movies.isEmpty());
		
		final var round = new RoundEntity();
		round.setBattle(BattleEntity.builder().id(idBattle).build());
		round.setMovies(movies);
		
		final RoundEntity roundSaved = roundRepository.save(round);
		
		return roundSaved;
	}
	
	
		
}
