package sports.trademarket.exceptions.spring;

public class DuplicationException extends RuntimeException {

    public DuplicationException() {
        super();
    }

    public DuplicationException(String message) {
        super(message);
    }
}
