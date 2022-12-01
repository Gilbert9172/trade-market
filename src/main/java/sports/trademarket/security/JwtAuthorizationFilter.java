package sports.trademarket.security;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import sports.trademarket.dto.CustomUserDetails;
import sports.trademarket.entity.Agent;
import sports.trademarket.entity.enumType.RoleType;
import sports.trademarket.exceptions.security.EmptyTokenException;
import sports.trademarket.exceptions.security.ExpiredAccessTokenException;
import sports.trademarket.exceptions.security.NotValidTokenException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

import static sports.trademarket.dto.enumType.AuthConstants.*;
import static sports.trademarket.utililty.JwtUtil.*;

@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected boolean shouldNotFilter(@NotNull HttpServletRequest request) throws ServletException {

        String[] EXCLUDE_URL = {"/swagger-ui", "/swagger-resources", "/v2/api-docs",
                                "/v1/agent/login","/v1/agent/join",
                                "/v1/agency", "/v1/email", "/v1/player"};
        log.info("경로 = {}", request.getServletPath());
        return Arrays.stream(EXCLUDE_URL).anyMatch(url -> request.getServletPath().startsWith(url));
    }

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
        try {
            String accessToken = extractTokenFromRequestHeader(request, AUTH_HEADER.getName());
            isValidToken(accessToken);
            checkAuthorization(accessToken);
            filterChain.doFilter(request, response);

        } catch (NullPointerException e) {
            throw new EmptyTokenException();
        } catch (ExpiredJwtException e) {
            try {
                String refreshToken = extractTokenFromRequestHeader(request, RE_AUTH_HEADER.getName());
                isValidRefreshToken(refreshToken);
                Agent userCredential = newAccessTokenMadeByRefreshToken(refreshToken);
                String newAccessToken = generate(userCredential);
                throw new ExpiredAccessTokenException(TOKEN_TYPE.getName() + " " + newAccessToken);
            } catch (ExpiredJwtException ex) {
                throw new NotValidTokenException();
            }
        }
    }

    private static String extractTokenFromRequestHeader(HttpServletRequest request, String header) {
        String bearerToken = request.getHeader(header);
        return getTokenFromBearer(bearerToken);
    }

    private static Agent newAccessTokenMadeByRefreshToken(String refreshToken) {
        String roleFromToken = getTypeFromToken(refreshToken);
        return  Agent.builder()
                .agentId(getIdFromToken(refreshToken))
                .roleType(checkRoleType(roleFromToken))
                .email(getEmailFromToken(refreshToken))
                .build();
    }

    private static void settingResponseHeader(HttpServletResponse response, String newAccessToken, String reToken) {
        String token = TOKEN_TYPE + " " + newAccessToken;
        response.setHeader(AUTH_HEADER.getName(), token);
        response.setHeader(RE_AUTH_HEADER.getName(), TOKEN_TYPE + " " + reToken);
    }

    public void checkAuthorization(String token) {
        String roleFromToken = getTypeFromToken(token);
        CustomUserDetails userDetail = new CustomUserDetails(Agent.builder()
                .roleType(checkRoleType(roleFromToken)).build());

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(null, null, userDetail.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private static RoleType checkRoleType(String role) {
        RoleType checkRole = null;

        for (RoleType value : RoleType.values()) {
            if (value.getName().equals(role)) {
                checkRole = value;
            }
        }
        return checkRole;
    }

}
