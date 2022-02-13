package br.com.letscode.moviesbattle.data.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "round")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoundEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonIgnore
	@ToString.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_battle")
	private BattleEntity battle;
	
	@JsonIgnore
	@ToString.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_movie_right")
	private MovieEntity movieRight;
	
	@ToString.Exclude
	@ManyToMany
	@JoinTable(name = "round_movie", joinColumns = @JoinColumn(name = "id_round"), inverseJoinColumns = @JoinColumn(name = "id_movie"))
	private List<MovieEntity> movies;
		
	@Column
	private Boolean correct;
		
	@Column(nullable = true)
	private Integer number;

}
