package br.com.letscode.moviesbattle.controller;

import org.jboss.logging.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.letscode.moviesbattle.exception.UnavailableMoviesException;
import br.com.letscode.moviesbattle.exception.UnavailableQuizException;
import br.com.letscode.moviesbattle.exception.UnavailableRoundException;
import br.com.letscode.moviesbattle.exception.UserNotAllowedException;
import br.com.letscode.moviesbattle.exception.WrongMovieException;

@ControllerAdvice
public class ControllerAdviceExceptionHandler {
	
	final static Logger logger = Logger.getLogger(ControllerAdviceExceptionHandler.class);
	
	@ExceptionHandler(UnavailableMoviesException.class)
	public ResponseEntity<String> handleUnavailableMoviesException(final UnavailableMoviesException e) {
		logger.error(e.getMessage());
		return ResponseEntity.badRequest().body(e.getMessage());		
	}
	
	@ExceptionHandler(UnavailableQuizException.class)
	public ResponseEntity<String> handleUnavailableQuizException(final UnavailableQuizException e) {
		logger.error(e.getMessage());
		return ResponseEntity.badRequest().body(e.getMessage());		
	}
	
	@ExceptionHandler(UnavailableRoundException.class)
	public ResponseEntity<String> handleUnavailableRoundException(final UnavailableRoundException e) {
		logger.error(e.getMessage());
		return ResponseEntity.badRequest().body(e.getMessage());		
	}
	
	@ExceptionHandler(UserNotAllowedException.class)
	public ResponseEntity<String> handleUserNotAllowedException(final UserNotAllowedException e) {
		logger.error(e.getMessage());
		return ResponseEntity.badRequest().body(e.getMessage());		
	}
	
	@ExceptionHandler(WrongMovieException.class)
	public ResponseEntity<String> handleWrongMovieException(final WrongMovieException e) {
		logger.error(e.getMessage());
		return ResponseEntity.badRequest().body(e.getMessage());		
	}
	
}
