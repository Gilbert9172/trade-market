package sports.trademarket.security.handler;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static sports.trademarket.dto.enumType.AuthConstants.*;
import static sports.trademarket.security.ErrorResponseConstants.hasNoAuthorities;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {

        commonResponseOptions(response);
        response.setStatus(response.SC_FORBIDDEN);
        response.getWriter().print(hasNoAuthorities());

    }

    public void commonResponseOptions(HttpServletResponse response) {
        response.addHeader(CONTENT_TYPE.getName(), APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(ENCODING_TYPE.getName());
    }

}
