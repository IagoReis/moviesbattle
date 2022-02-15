package br.com.letscode.moviesbattle.business;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.letscode.moviesbattle.dto.MovieDto;
import br.com.letscode.moviesbattle.repository.MovieRepository;


@ExtendWith(MockitoExtension.class)
public class MovieBusinessTest {

	@Mock
    private MovieRepository repository;

    @InjectMocks
    private MovieBusiness business;
    
    @Test
    void insertTest() {
    	    	
    	final MovieDto movie = new MovieDto();
    	movie.setId("1");
    	movie.setRating("8.00");
    	movie.setTitle("Titulo");
    	movie.setYear("2000");
    	movie.setType("Type");
    	
    	final MovieDto response = business.insert(movie);
    	
    	Assertions.assertEquals(movie.getId(), response.getId());
    }

}
