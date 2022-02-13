package br.com.letscode.moviesbattle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.letscode.moviesbattle.data.entity.RoundEntity;

@Repository
public interface RoundRepository extends JpaRepository<RoundEntity, Long> {
	
	List<RoundEntity> findByBattleId(final Long idBattle);

}
