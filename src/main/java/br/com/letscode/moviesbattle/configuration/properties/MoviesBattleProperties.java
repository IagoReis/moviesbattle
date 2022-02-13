package br.com.letscode.moviesbattle.configuration.properties;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import br.com.letscode.moviesbattle.data.entity.UserEntity;
import lombok.Data;

@Data
@ConfigurationProperties(prefix = "moviesbattle")
public class MoviesBattleProperties {
	
	private Init init;
	
	@Data
	public static class Init {
		
		private List<String> movies;
		
		private List<UserEntity> users;
		
	}

}
