package br.com.letscode.moviesbattle.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import br.com.letscode.moviesbattle.data.entity.UserEntity;
import br.com.letscode.moviesbattle.repository.UserRepository;


@Component
public class AuthFacade {
	
	@Autowired
	private UserRepository repository;
	
	public Authentication getAuthentication() {
		
		final var authentication = SecurityContextHolder.getContext().getAuthentication();
		
		return authentication;
	}

	public UserEntity getUser() {

		final var userDefault = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				
		final var user = repository.findByLogin(userDefault.getUsername()).get();
		
		return user;
	}

}
