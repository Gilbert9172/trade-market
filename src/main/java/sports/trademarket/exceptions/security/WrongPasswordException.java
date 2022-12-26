package sports.trademarket.exceptions.security;

import org.springframework.security.authentication.BadCredentialsException;

public class WrongPasswordException extends BadCredentialsException {
    public WrongPasswordException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public WrongPasswordException(String msg) {
        super(msg);
    }
}
