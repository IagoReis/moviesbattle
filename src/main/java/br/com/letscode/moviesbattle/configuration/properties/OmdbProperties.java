package br.com.letscode.moviesbattle.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "omdb")
public class OmdbProperties {
	
	private String endpoint;

}
