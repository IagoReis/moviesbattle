package br.com.letscode.moviesbattle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.letscode.moviesbattle.data.entity.BattleEntity;

@Repository
public interface BattleRepository extends JpaRepository<BattleEntity, Long> {
	
	@Query(value = "SELECT * from battle WHERE status = 1 ORDER BY percent DESC LIMIT 10", nativeQuery = true)
	List<BattleEntity> getRanking();

}
