package semestrovka.module.exceptions;

public class FileSystemManagerException extends ValidationException {

    public FileSystemManagerException() {
    }

    public FileSystemManagerException(String message) {
        super(message);
    }

    public FileSystemManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileSystemManagerException(Throwable cause) {
        super(cause);
    }
}
