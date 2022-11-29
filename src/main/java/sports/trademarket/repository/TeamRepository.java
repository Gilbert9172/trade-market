package sports.trademarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sports.trademarket.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
