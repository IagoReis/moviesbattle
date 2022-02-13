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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "battle")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BattleEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
	private UserEntity user;
	
	@ManyToMany
	@JoinTable(name = "battle_movie", joinColumns = @JoinColumn(name = "id_battle"), inverseJoinColumns = @JoinColumn(name = "id_movie"))
	private List<MovieEntity> movies;
	
	@Column(nullable = false)
	private Integer attempt = 0;
	
}
