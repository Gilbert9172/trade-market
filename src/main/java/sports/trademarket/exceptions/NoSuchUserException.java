package sports.trademarket.exceptions;

import org.springframework.security.core.AuthenticationException;

public class NoSuchUserException extends AuthenticationException {

    public NoSuchUserException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public NoSuchUserException(String msg) {
        super(msg);
    }
}
