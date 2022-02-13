package br.com.letscode.moviesbattle.business;

import java.util.ArrayList;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.letscode.moviesbattle.data.entity.BattleEntity;
import br.com.letscode.moviesbattle.data.entity.MovieEntity;
import br.com.letscode.moviesbattle.data.entity.RoundEntity;
import br.com.letscode.moviesbattle.data.entity.UserEntity;
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
	
	public BattleEntity getById(final Long id) {
		
		final var response = battleRepository.findById(id).get();
		response.setRounds(roundRepository.findByBattleId(id));
		
		return response;
	}
	
	public BattleEntity start(final UserEntity user) {
		
		final var battle = new BattleEntity();		
		battle.setUser(user);
		
		final var battleSaved = battleRepository.save(battle);
		
		final var firstMovieOptional = movieRepository.findRandon();
		
		if (firstMovieOptional.isEmpty()) {
			throw new RuntimeException("PRIMEIRO FILME NÃO DISPONÍVEL"); 
		}
		
		final var secondMovieOptional = movieRepository.findAvailable(Collections.singletonList(firstMovieOptional.get().getId()));
		
		if (secondMovieOptional.isEmpty()) {
			throw new RuntimeException("SEGUNDO FILME NÃO DISPONÍVEL"); 
		}
		
		final var movies = new ArrayList<MovieEntity>();
		movies.add(firstMovieOptional.get());
		movies.add(secondMovieOptional.get());
		
		final var round = new RoundEntity();
		round.setBattle(battle);
		round.setMovies(movies);
		
		roundRepository.save(round);
				
		return this.getById(battleSaved.getId());
		
		/*
		final var movies = new ArrayList<MovieEntity>();
		
		var available = movieRepository.findRandon();
		
		List<String> unavailableMovies = new ArrayList<>();
		
		unavailableMovies.add(available.getId());
		
		MovieEntity secondAvailable;
		
		do {
			
			secondAvailable = movieRepository.findAvailable(unavailableMovies, user.getId());
			
			if (Objects.isNull(secondAvailable)) {
				
				available = movieRepository.findAvailable(unavailableMovies);
				
				if (Objects.isNull(available)) {
					throw new RuntimeException("NÃO TEM MAIS FILMES DISPONÍVEIS");
				}
				
				unavailableMovies.add(available.getId());
				
				continue;
			}
			
			movies.add(available);
			movies.add(secondAvailable);
		}
		while(movies.size() < 2);
		
		final var entity = new BattleEntity();		
		entity.setUser(user);
		entity.setMovies(movies);
		
		battleRepository.save(entity);
		*/
	}
		
}
