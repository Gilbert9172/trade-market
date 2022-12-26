package sports.trademarket.exceptions.security;

public class EmptyTokenException extends RuntimeException {

    public EmptyTokenException() {
        super();
    }

    public EmptyTokenException(String message) {
        super(message);
    }

    public EmptyTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyTokenException(Throwable cause) {
        super(cause);
    }
}
