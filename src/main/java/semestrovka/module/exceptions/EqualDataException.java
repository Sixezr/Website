package semestrovka.module.exceptions;

public class EqualDataException extends ValidationException {
    public EqualDataException() {
    }

    public EqualDataException(String message) {
        super(message);
    }

    public EqualDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public EqualDataException(Throwable cause) {
        super(cause);
    }

    public EqualDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
