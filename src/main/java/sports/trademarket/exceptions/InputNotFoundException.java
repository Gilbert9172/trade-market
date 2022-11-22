package sports.trademarket.exceptions;

import org.springframework.security.core.AuthenticationException;

public class InputNotFoundException extends AuthenticationException {
    public InputNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public InputNotFoundException(String msg) {
        super(msg);
    }
}
