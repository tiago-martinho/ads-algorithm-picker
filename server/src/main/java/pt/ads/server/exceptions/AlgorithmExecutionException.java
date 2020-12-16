package pt.ads.server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * A algorithm exception that occurs when executing an algorithm.
 */
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class AlgorithmExecutionException extends AlgorithmException {

	public AlgorithmExecutionException() {
	}

	public AlgorithmExecutionException(String message) {
		super(message);
	}

	public AlgorithmExecutionException(String message, Throwable cause) {
		super(message, cause);
	}

	public AlgorithmExecutionException(Throwable cause) {
		super(cause);
	}

	public AlgorithmExecutionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
