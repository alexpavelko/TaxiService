package exception;

public class ValidateException extends Exception {

    public ValidateException() {
        super();
    }

    public ValidateException(Throwable cause) {
        super(cause);
    }

    public ValidateException(String message) {
        super(message);
    }
}
