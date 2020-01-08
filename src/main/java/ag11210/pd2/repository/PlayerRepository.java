package ag11210.pd2.repository;

import ag11210.pd2.dto.BasicPlayerStatisticsDto;
import ag11210.pd2.model.PlayerEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<PlayerEntity, Long> {

    @Query("select new ag11210.pd2.dto.BasicPlayerStatisticsDto(" +
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
            " order by 5 desc, 6 desc")
    List<BasicPlayerStatisticsDto> getBasicPlayerStatistics(Pageable pageable);

    @Query("select distinct p" +
            " from Player p" +
            " left join fetch p.playerGames pg" +
            " left join fetch pg.goals" +
            " left join fetch pg.assistedGoals" +
            " left join fetch pg.fouls" +
            " left join fetch pg.substitutes" +
            " left join fetch pg.substitutions" +
            " left join fetch pg.game g" +
            " where p.team.name = :team")
    List<PlayerEntity> getForTeamStatistics(@Param("team") String team);
}
