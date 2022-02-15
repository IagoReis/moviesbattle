package br.com.letscode.moviesbattle.data.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "movie")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MovieEntity {
	
	@Id	
	private String id;
	
	@Column(nullable = false)
	private String title;
	
	@Column(nullable = false)
	private Integer year;
	
	@JsonIgnore
	@Column(nullable = false)
	private BigDecimal rating;

}
