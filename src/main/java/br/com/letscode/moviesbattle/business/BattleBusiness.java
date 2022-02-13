package br.com.letscode.moviesbattle.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.letscode.moviesbattle.data.entity.UserEntity;
import br.com.letscode.moviesbattle.repository.BattleRepository;

@Service
public class BattleBusiness {
	
	@Autowired
	private BattleRepository repository;
	
	public void start(final UserEntity user) {
		
	}

}
