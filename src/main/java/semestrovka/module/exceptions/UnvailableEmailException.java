package semestrovka.module.exceptions;

public class UnvailableEmailException extends ValidationException {
    public UnvailableEmailException() {
    }

    public UnvailableEmailException(String message) {
        super(message);
    }

    public UnvailableEmailException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnvailableEmailException(Throwable cause) {
        super(cause);
    }

    public UnvailableEmailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
