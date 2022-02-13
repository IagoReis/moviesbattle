package br.com.letscode.moviesbattle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import br.com.letscode.moviesbattle.configuration.properties.OmdbProperties;


@SpringBootApplication
@ConfigurationPropertiesScan(basePackageClasses = OmdbProperties.class)
public class MoviesbattleApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoviesbattleApplication.class, args);
	}

}
