package br.com.letscode.moviesbattle.configuration.properties;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "omdb")
public class OmdbProperties {
	
	private String endpoint;
	
	private List<String> movies;

}
