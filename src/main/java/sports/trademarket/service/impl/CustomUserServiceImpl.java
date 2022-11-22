package sports.trademarket.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sports.trademarket.dto.CustomUserDetails;
import sports.trademarket.entity.Agent;
import sports.trademarket.exceptions.NoSuchUserException;
import sports.trademarket.repository.AgentRepository;
import sports.trademarket.service.CustomUserDetailsService;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomUserServiceImpl implements CustomUserDetailsService {

    private final AgentRepository agentRepository;

    @Override
    public UserDetails loadAgentByEmail(String email) throws UsernameNotFoundException {

        Agent user = agentRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchUserException("No Such User"));

        return new CustomUserDetails(user);
    }
}
