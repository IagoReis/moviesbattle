package br.com.letscode.moviesbattle.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizDto {
	
	@JsonProperty("id_quiz")
	private String idQuiz;
	
	@JsonProperty("status")
	private String status;
	
	@JsonProperty("rodadas")
	private Integer rodadas;
	
	@JsonProperty("erros")
	private Integer erros;
	
	@JsonProperty("pontos")
	private Integer pontos;
	
	@JsonProperty("porcentagem_acerto")
	private BigDecimal porcentagemAcerto;

}
