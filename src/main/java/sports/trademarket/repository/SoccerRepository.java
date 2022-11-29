package sports.trademarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sports.trademarket.entity.Soccer;

import java.util.Optional;

@Repository
public interface SoccerRepository extends JpaRepository<Soccer, Long> {

    @Query("select s from Soccer s where s.fifaRegNum = :fifaRegNum")
    Optional<Soccer> findByFifaRegNum(@Param("fifaRegNum") String fifaRegNum);
}
