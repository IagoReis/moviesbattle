package br.com.letscode.moviesbattle.configuration.interceptor;

import java.io.IOException;
import java.net.URI;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class OmdbInterceptor implements ClientHttpRequestInterceptor {

	@Override
	public ClientHttpResponse intercept(final HttpRequest request, final byte[] body, final ClientHttpRequestExecution execution) throws IOException {		
		
		final URI uri = UriComponentsBuilder.fromHttpRequest(request).queryParam("apikey", "2e021164").build().toUri();

		final HttpRequest newRequest = new HttpRequestWrapper(request) {
			@Override
			public URI getURI() {
				return uri;
			}
		};
		
		return execution.execute(newRequest, body);
	}

}
