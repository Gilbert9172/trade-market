package sports.trademarket.exceptions.security;

public class ExpiredAccessTokenException extends RuntimeException {
    public ExpiredAccessTokenException(String message) {
        super(message);
    }
}
