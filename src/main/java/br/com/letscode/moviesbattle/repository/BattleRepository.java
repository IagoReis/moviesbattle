package br.com.letscode.moviesbattle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.letscode.moviesbattle.data.entity.BattleEntity;

@Repository
public interface BattleRepository extends JpaRepository<BattleEntity, Long> {

}
