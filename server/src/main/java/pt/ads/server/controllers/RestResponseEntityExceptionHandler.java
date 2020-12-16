package pt.ads.server.controllers;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pt.ads.server.exceptions.AlgorithmExecutionException;
import pt.ads.server.exceptions.AlgorithmInputsException;

/**
 * Handles any exceptions that have been raised while fulfilling a request.
 */
@Slf4j
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { AlgorithmInputsException.class })
	protected ResponseEntity<Object> handleBadRequest(RuntimeException ex, WebRequest request) {
		return handle(HttpStatus.BAD_REQUEST, ex, request);
	}

	@ExceptionHandler(value = { Exception.class, AlgorithmExecutionException.class })
	protected ResponseEntity<Object> handleInternalServerError(RuntimeException ex, WebRequest request) {
		return handle(HttpStatus.INTERNAL_SERVER_ERROR, ex, request);
	}

	private ResponseEntity<Object> handle(HttpStatus status, RuntimeException ex, WebRequest request) {
		log.error("Error processing request", ex);
		ErrorResponse response = new ErrorResponse(ex.getMessage());
		return handleExceptionInternal(ex, response, new HttpHeaders(), status, request);
	}

	@Data
	private static class ErrorResponse {
		public final boolean error = true;
		public final String message;
	}

}