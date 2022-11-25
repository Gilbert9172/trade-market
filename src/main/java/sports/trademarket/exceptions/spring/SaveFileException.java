package sports.trademarket.exceptions.spring;

public class SaveFileException extends RuntimeException {

    public SaveFileException() {
        super();
    }

    public SaveFileException(String message) {
        super(message);
    }

    public SaveFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public SaveFileException(Throwable cause) {
        super(cause);
    }
}
