package br.com.letscode.moviesbattle.exception;

public class UnavailableMoviesException extends RuntimeException {

	private static final long serialVersionUID = 831958928082092015L;
	
	public UnavailableMoviesException(final String message) {
        super(message);
    }

}
