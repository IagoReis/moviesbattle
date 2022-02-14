package br.com.letscode.moviesbattle.security;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import br.com.letscode.moviesbattle.repository.UserRepository;


@Component
public class BattleMovieUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		
		final var user = repository.findByLogin(username);
		
		if(user.isEmpty()) {
			return null;
		}
		
		final var userResponse = new User(user.get().getLogin(), user.get().getPassword(), Collections.emptyList());
		
		return userResponse;
	}

}
