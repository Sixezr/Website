package semestrovka.module.exceptions;

public class WeakPasswordExceptions extends ValidationException {
    public WeakPasswordExceptions() {
    }

    public WeakPasswordExceptions(String message) {
        super(message);
    }

    public WeakPasswordExceptions(String message, Throwable cause) {
        super(message, cause);
    }

    public WeakPasswordExceptions(Throwable cause) {
        super(cause);
    }

    public WeakPasswordExceptions(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
