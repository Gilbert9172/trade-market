package sports.trademarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sports.trademarket.entity.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long>  {


}
