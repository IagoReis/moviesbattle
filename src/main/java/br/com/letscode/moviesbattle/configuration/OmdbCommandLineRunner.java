package br.com.letscode.moviesbattle.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import br.com.letscode.moviesbattle.business.MovieBusiness;
import br.com.letscode.moviesbattle.configuration.properties.OmdbProperties;
import br.com.letscode.moviesbattle.integration.OmdbIntegration;

@Configuration
public class OmdbCommandLineRunner implements CommandLineRunner {
	
	@Autowired
	private OmdbProperties properties;
	
	@Autowired
	private OmdbIntegration integration;
	
	@Autowired
	private MovieBusiness business;
	
	@Override
	public void run(final String... args) throws Exception {
		
		for(final String id : properties.getMovies()) {
			final var movie = integration.getById(id);
			business.insert(movie);
		}
		
	}

}
