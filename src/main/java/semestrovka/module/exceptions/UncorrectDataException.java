package semestrovka.module.exceptions;

public class UncorrectDataException extends ValidationException {
    public UncorrectDataException() {
    }

    public UncorrectDataException(String message) {
        super(message);
    }

    public UncorrectDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public UncorrectDataException(Throwable cause) {
        super(cause);
    }

    public UncorrectDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
