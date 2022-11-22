package sports.trademarket.security.handler;

import org.jetbrains.annotations.NotNull;
import org.springframework.web.filter.OncePerRequestFilter;
import sports.trademarket.exceptions.EmptyTokenException;
import sports.trademarket.exceptions.NotValidTokenException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static sports.trademarket.dto.enumType.AuthConstants.CONTENT_TYPE;
import static sports.trademarket.dto.enumType.AuthConstants.ENCODING_TYPE;
import static sports.trademarket.security.ErrorResponseConstants.*;

public class ExceptionHandlingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {

        try {
            filterChain.doFilter(request, response);
        } catch (EmptyTokenException e) {
            eachResponseOptions(response, response.SC_UNAUTHORIZED, emptyToken());
        } catch (NotValidTokenException e) {
            eachResponseOptions(response, response.SC_UNAUTHORIZED, loginAgain());
        }

    }

    public void eachResponseOptions(HttpServletResponse response, int status, String msg) throws IOException {
        response.addHeader(CONTENT_TYPE.getName(), APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(ENCODING_TYPE.getName());
        response.setStatus(status);
        response.getWriter().print(msg);
    }

}
