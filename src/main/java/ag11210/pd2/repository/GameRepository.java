package ag11210.pd2.repository;

import ag11210.pd2.model.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<GameEntity, Long> {

    @Query("select g from Game g join g.players p where g.date = :date and p.team = :team")
    Optional<GameEntity> findByDateAndTeam(@Param("date") LocalDate date, @Param("team") String team);
}
