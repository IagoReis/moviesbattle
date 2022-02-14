package br.com.letscode.moviesbattle.dto;

import br.com.letscode.moviesbattle.data.entity.RoundEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizDto {
	
	private RoundEntity round;

}
