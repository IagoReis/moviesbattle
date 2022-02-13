package br.com.letscode.moviesbattle.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieDto {
	
    @JsonProperty("Title")
	private String title;
    
    @JsonProperty("imdbRating")
	private String rating;
    
    @JsonProperty("imdbID")
	private String id;
    
    @JsonProperty("Type")
	private String type;

}
