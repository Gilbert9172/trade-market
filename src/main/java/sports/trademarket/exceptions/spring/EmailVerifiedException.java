package sports.trademarket.exceptions.spring;

public class EmailVerifiedException extends RuntimeException {
    public EmailVerifiedException(String message) {
        super(message);
    }
}
