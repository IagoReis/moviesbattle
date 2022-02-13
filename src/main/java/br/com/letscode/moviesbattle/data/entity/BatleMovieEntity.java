package br.com.letscode.moviesbattle.data.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
@Entity
@Table(name = "battle_movie")
@NoArgsConstructor
@AllArgsConstructor
@Data
*/
public class BatleMovieEntity implements Serializable {
	
	/*
	private static final long serialVersionUID = -2623596961707392088L;

	@Id
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_battle")
	private BattleEntity battle;
	
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_movie")
	private MovieEntity movie;
	*/
}
