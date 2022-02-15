package br.com.letscode.moviesbattle.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoundDto {
	
	@JsonProperty("id_rodada")
	private Long idRodada;
	
	@JsonProperty("rodada")
	private Integer rodada;
	
	@JsonProperty("filmes")
	private List<MovieResponseDto> filmes;

}
