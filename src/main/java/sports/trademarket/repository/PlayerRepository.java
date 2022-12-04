package sports.trademarket.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sports.trademarket.entity.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long>  {

    @Query("select p from Player p where p.agent.agentId = :agentId")
    Page<Player> findClientByAgentId(@Param("agentId") Long agentId, Pageable pageable);

}
