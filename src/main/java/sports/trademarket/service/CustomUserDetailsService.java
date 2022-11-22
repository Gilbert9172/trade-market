package sports.trademarket.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface CustomUserDetailsService {
    UserDetails loadAgentByEmail(String email);
}
