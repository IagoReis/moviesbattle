package br.com.letscode.moviesbattle.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.letscode.moviesbattle.data.entity.MovieEntity;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, String> {
	
	@Query(value = "SELECT * FROM movie ORDER BY RAND() LIMIT 1", nativeQuery = true)
	Optional<MovieEntity> findRandon();
	
	@Query(value = "SELECT * FROM movie WHERE id NOT IN(:unavailableMovies) ORDER BY RAND() LIMIT 1", nativeQuery = true)
	Optional<MovieEntity> findAvailable(final List<String> unavailableMovies);
	
}
