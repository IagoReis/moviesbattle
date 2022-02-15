package br.com.letscode.moviesbattle.exception;

public class UnavailableRoundException extends RuntimeException {

	private static final long serialVersionUID = 831958928082092015L;
	
	public UnavailableRoundException() {
        super("Rodada indisponível");
    }
	
	public UnavailableRoundException(final String message) {
        super(message);
    }

}
