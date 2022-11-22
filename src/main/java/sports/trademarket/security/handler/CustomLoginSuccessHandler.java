package sports.trademarket.security.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import sports.trademarket.dto.CustomUserDetails;
import sports.trademarket.entity.Agent;
import sports.trademarket.utililty.JwtUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.MediaType.*;
import static sports.trademarket.dto.enumType.AuthConstants.*;
import static sports.trademarket.security.ErrorResponseConstants.*;

public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        final Agent loginUser = ((CustomUserDetails) authentication.getPrincipal()).getAgent();
        final String accessToken = JwtUtil.generate(loginUser);

        response.addHeader(AUTH_HEADER.getName(), TOKEN_TYPE.getName() + " " + accessToken);
        response.addHeader(RE_AUTH_HEADER.getName(), TOKEN_TYPE.getName() + " " + JwtUtil.reGenerate(loginUser));
        response.addHeader(CONTENT_TYPE.getName(), APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(ENCODING_TYPE.getName());
        response.setStatus(response.SC_OK);
        response.getWriter().println(loginSuccessMessage());
    }
}
