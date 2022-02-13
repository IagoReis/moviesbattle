package br.com.letscode.moviesbattle.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.letscode.moviesbattle.data.entity.BattleEntity;
import br.com.letscode.moviesbattle.data.entity.MovieEntity;
import br.com.letscode.moviesbattle.data.entity.UserEntity;
import br.com.letscode.moviesbattle.repository.BattleRepository;
import br.com.letscode.moviesbattle.repository.MovieRepository;

@Service
public class BattleBusiness {
	
	@Autowired
	private BattleRepository battleRepository;
	
	@Autowired
	private MovieRepository movieRepository;
	
	public void start(final UserEntity user) {		
		
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
		
	}

}
