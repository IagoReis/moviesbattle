package br.com.letscode.moviesbattle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.letscode.moviesbattle.data.entity.MovieEntity;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, String> {

}
