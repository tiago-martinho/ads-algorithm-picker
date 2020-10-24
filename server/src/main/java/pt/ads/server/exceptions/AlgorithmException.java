package pt.ads.server.exceptions;

public class AlgorithmException extends RuntimeException {

	public AlgorithmException() {
	}

	public AlgorithmException(String message) {
		super(message);
	}

	public AlgorithmException(String message, Throwable cause) {
		super(message, cause);
	}

	public AlgorithmException(Throwable cause) {
		super(cause);
	}

	public AlgorithmException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
