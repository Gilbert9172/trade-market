package sports.trademarket.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import sports.trademarket.dto.CustomUserDetails;
import sports.trademarket.exceptions.WrongPasswordException;
import sports.trademarket.repository.AgentRepository;
import sports.trademarket.service.CustomUserDetailsService;

@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final CustomUserDetailsService customUserDetailsService;
    private final AgentRepository agentRepository;
    private final PasswordEncoder encoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        final UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;

        String agentEmail = token.getName();
        String agentPassword = (String) token.getCredentials();

        CustomUserDetails loginAgentDetails = (CustomUserDetails) customUserDetailsService.loadAgentByEmail(agentEmail);

        if (!encoder.matches(agentPassword, loginAgentDetails.getPassword())) {
            throw new WrongPasswordException("Wrong Password");
        }

        return new UsernamePasswordAuthenticationToken(loginAgentDetails, agentPassword, loginAgentDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
