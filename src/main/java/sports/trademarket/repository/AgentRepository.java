package sports.trademarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sports.trademarket.entity.Agent;

import java.util.Optional;

public interface AgentRepository extends JpaRepository<Agent, Long> {

    Optional<Agent> findByEmail(String email);

}
