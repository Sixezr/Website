package semestrovka.module.exceptions;

public class EmptyParametrException extends ValidationException {
    public EmptyParametrException() {
    }

    public EmptyParametrException(String message) {
        super(message);
    }

    public EmptyParametrException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyParametrException(Throwable cause) {
        super(cause);
    }

    public EmptyParametrException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
