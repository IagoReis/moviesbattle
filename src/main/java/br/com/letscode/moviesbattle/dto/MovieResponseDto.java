package br.com.letscode.moviesbattle.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieResponseDto {
	
	@JsonProperty("id_filme")
	private String idFilme;
	
    @JsonProperty("titulo")
	private String titulo;
    
    @JsonProperty("ano")
	private String ano;

}
