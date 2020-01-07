package ag11210.pd2.repository;

import ag11210.pd2.model.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<GameEntity, Long> {

    @Query("select case when count(g) > 0 then true else false end" +
            " from Game g" +
            " join g.playerGames pg" +
            " join pg.player p" +
            " where g.date = :date and p.team.name = :team")
    boolean existsByDateAndTeam(@Param("date") LocalDate date, @Param("team") String team);

    @Query("select distinct g" +
            " from Game g" +
            " join fetch g.referee " +
            " join fetch g.playerGames pg" +
            " join fetch pg.player p" +
            " left join fetch pg.goals")
    List<GameEntity> getForTournamentTable();
}
