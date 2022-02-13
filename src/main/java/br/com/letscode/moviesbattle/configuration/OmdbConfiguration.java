package br.com.letscode.moviesbattle.configuration;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import br.com.letscode.moviesbattle.configuration.interceptor.OmdbInterceptor;

@Configuration
public class OmdbConfiguration {
	
	@Autowired
	private OmdbInterceptor omdbInterceptor;
	
	@Bean(name = "OmdbRestTemplate")
	public RestTemplate getRestTemplate() {
		
		final RestTemplate restTemplate = new RestTemplate();
		
		restTemplate.setInterceptors(Collections.singletonList(omdbInterceptor));
		
		return restTemplate;
	}
	
}
