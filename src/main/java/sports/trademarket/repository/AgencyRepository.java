package sports.trademarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sports.trademarket.entity.Agency;

@Repository
public interface AgencyRepository extends JpaRepository<Agency, Long> {

}
