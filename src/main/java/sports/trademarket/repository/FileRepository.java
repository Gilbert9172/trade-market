package sports.trademarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sports.trademarket.entity.ProfileImg;

public interface FileRepository extends JpaRepository<ProfileImg, Long> {
}
