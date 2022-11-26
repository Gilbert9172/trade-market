package sports.trademarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sports.trademarket.entity.Agent;

import java.util.Optional;

public interface AgentRepository extends JpaRepository<Agent, Long> {

    @Query("select a from Agent a where a.email =:email")
    Optional<Agent> findByEmail(@Param("email") String email);

}
