package br.com.letscode.moviesbattle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.letscode.moviesbattle.data.entity.MovieEntity;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, String> {
	
	@Query(value = "SELECT * FROM movie ORDER BY RAND() LIMIT 1", nativeQuery = true)
	MovieEntity findRandon();
	
	@Query(value = "SELECT * FROM movie WHERE id NOT IN(:unavailableMovies) ORDER BY RAND() LIMIT 1", nativeQuery = true)
	MovieEntity findAvailable(final List<String> unavailableMovies);
	
	@Query(value = "" +
			"SELECT * FROM movie mm\n" + 
			"WHERE mm.id NOT IN (:unavailableMovies)\n" + 
			"AND mm.id not in (\n" + 
			"    SELECT m.id\n" + 
			"    FROM battle b\n" + 
			"    INNER JOIN battle_movie bm ON bm.id_battle = b.id\n" + 
			"    INNER JOIN movie m ON m.id = bm.id_movie\n" + 
			"    WHERE b.id_user = :idUser\n" + 
			"    AND m.id NOT IN (:unavailableMovies)\n" + 
			")" +
			"LIMIT 1", nativeQuery = true)
	MovieEntity findAvailable(final List<String> unavailableMovies, final Long idUser);
	
}
