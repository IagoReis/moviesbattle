package br.com.letscode.moviesbattle.business;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.letscode.moviesbattle.data.entity.MovieEntity;
import br.com.letscode.moviesbattle.dto.MovieDto;
import br.com.letscode.moviesbattle.repository.MovieRepository;

@Service
public class MovieBusiness {

	@Autowired
	private MovieRepository repository;
	
	public MovieDto insert(final MovieDto movie) {
		
		final var entity = new MovieEntity();
		entity.setId(movie.getId());
		entity.setTitle(movie.getTitle());
		entity.setYear(Integer.valueOf(movie.getYear()));
		entity.setRating(new BigDecimal(movie.getRating()));
		
		repository.save(entity);
		
		return movie;
	}
	
}
