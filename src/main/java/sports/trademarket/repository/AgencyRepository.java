package sports.trademarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sports.trademarket.entity.Agency;

import java.util.Optional;

@Repository
public interface AgencyRepository extends JpaRepository<Agency, Long> {

    @Query("select a from Agency a where a.homepageUrl =:url")
    Optional<Agency> findAgencyByUrl(@Param("url") String url);


}
