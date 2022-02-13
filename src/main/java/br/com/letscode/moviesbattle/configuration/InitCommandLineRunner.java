package br.com.letscode.moviesbattle.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import br.com.letscode.moviesbattle.business.MovieBusiness;
import br.com.letscode.moviesbattle.configuration.properties.MoviesBattleProperties;
import br.com.letscode.moviesbattle.data.entity.UserEntity;
import br.com.letscode.moviesbattle.integration.OmdbIntegration;
import br.com.letscode.moviesbattle.repository.UserRepository;

@Configuration
public class InitCommandLineRunner implements CommandLineRunner {
	
	@Autowired
	private MoviesBattleProperties properties;
	
	@Autowired
	private OmdbIntegration integration;
	
	@Autowired
	private MovieBusiness business;
	
	@Autowired
	private UserRepository repository;
	
	@Override
	public void run(final String... args) throws Exception {
		
		for(final String id : properties.getInit().getMovies()) {
			try {
				final var movie = integration.getById(id);
				business.insert(movie);	
			} catch (Exception e) {
				// TODO: handle exception
			}			
		}
		
		for(final UserEntity user : properties.getInit().getUsers()) {			
			try {
				repository.save(user);	
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}
		
	}

}
