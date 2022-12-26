package sports.trademarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sports.trademarket.entity.Contract;

public interface ContractRepository extends JpaRepository<Contract, Long> {

}
