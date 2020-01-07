package ag11210.pd2.repository;

import ag11210.pd2.dto.PlayerStatisticsDto;
import ag11210.pd2.model.PlayerEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<PlayerEntity, Long> {

    @Query("select new ag11210.pd2.dto.PlayerStatisticsDto(" +
            "  p.firstName" +
            ", p.lastName" +
            ", p.team.name" +
            ", p.number" +
            ", sum((select count(goals) from pg.goals goals))" +
            ", sum((select count(assists) from pg.assistedGoals assists)))" +
            " from Player p" +
            " join p.playerGames pg" +
            " where exists (select 1 from pg.goals)" +
            " or exists (select 1 from pg.assistedGoals)" +
            " group by p, p.firstName, p.lastName, p.team.name" +
            " order by 4 desc, 5 desc, 1, 2, 3")
    List<PlayerStatisticsDto> getPlayerStatistics(Pageable pageable);
}
