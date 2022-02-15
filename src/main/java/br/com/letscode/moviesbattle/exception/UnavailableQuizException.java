package br.com.letscode.moviesbattle.exception;

public class UnavailableQuizException extends RuntimeException {

	private static final long serialVersionUID = 831958928082092015L;
	
	public UnavailableQuizException() {
        super("Quiz indisponível");
    }
	
	public UnavailableQuizException(final String message) {
        super(message);
    }

}
