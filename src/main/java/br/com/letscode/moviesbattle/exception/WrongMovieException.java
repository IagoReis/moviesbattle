package br.com.letscode.moviesbattle.exception;

public class WrongMovieException extends RuntimeException {

	private static final long serialVersionUID = 831958928082092015L;
	
	public WrongMovieException() {
        super("Filme não encontrado");
    }
	
	public WrongMovieException(final String message) {
        super(message);
    }

}
