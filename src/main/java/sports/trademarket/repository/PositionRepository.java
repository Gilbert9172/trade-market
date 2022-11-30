package sports.trademarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sports.trademarket.entity.Position;

public interface PositionRepository extends JpaRepository<Position, Long> {
}
