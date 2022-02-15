package br.com.letscode.moviesbattle.exception;

public class UserNotAllowedException extends RuntimeException {

	private static final long serialVersionUID = -7812818195161232430L;

	public UserNotAllowedException() {
        super("Usuário não possui permissão para essa ação");
    }

	public UserNotAllowedException(final String message) {
        super(message);
    }

}
