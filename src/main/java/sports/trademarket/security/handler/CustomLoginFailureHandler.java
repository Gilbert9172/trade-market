package sports.trademarket.security.handler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import sports.trademarket.exceptions.security.InputNotFoundException;
import sports.trademarket.exceptions.security.NoSuchUserException;
import sports.trademarket.exceptions.security.WrongPasswordException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.MediaType.*;
import static sports.trademarket.dto.enumType.AuthConstants.CONTENT_TYPE;
import static sports.trademarket.dto.enumType.AuthConstants.ENCODING_TYPE;
import static sports.trademarket.security.ErrorResponseConstants.*;

public class CustomLoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        commonResponseOptions(response);

        if (exception instanceof InputNotFoundException) {
            eachResponseOptions(response, response.SC_BAD_REQUEST, noInput());
        } else if (exception instanceof NoSuchUserException) {
            eachResponseOptions(response, response.SC_UNAUTHORIZED, noSuchUser());
        } else if (exception instanceof WrongPasswordException) {
            eachResponseOptions(response, response.SC_BAD_REQUEST, wrongPassword());
        } else {
            eachResponseOptions(response, response.SC_BAD_REQUEST, loginFail());
        }
    }

    public void commonResponseOptions(HttpServletResponse response) {
        response.addHeader(CONTENT_TYPE.getName(), APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(ENCODING_TYPE.getName());
    }

    public void eachResponseOptions(HttpServletResponse response, int status, String msg) throws IOException {
        response.setStatus(status);
        response.getWriter().print(msg);
    }

}
