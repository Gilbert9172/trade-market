package sports.trademarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sports.trademarket.entity.Offer;

import java.util.Optional;

public interface OfferRepository extends JpaRepository<Offer, Long> {

    @Query("select o from Offer o where o.agent.agentId =:agentId and o.player.playerId =:playerId")
    Optional<Offer> findPreviousOffer(@Param("agentId") Long agentId, @Param("playerId") Long playerId);
}
