package pt.ads.server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * A algorithm exception that occurs with invalid user inputs.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AlgorithmInputsException extends AlgorithmException {

	public AlgorithmInputsException() {
	}

	public AlgorithmInputsException(String message) {
		super(message);
	}

	public AlgorithmInputsException(String message, Throwable cause) {
		super(message, cause);
	}

	public AlgorithmInputsException(Throwable cause) {
		super(cause);
	}

	public AlgorithmInputsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
