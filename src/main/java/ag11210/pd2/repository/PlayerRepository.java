package ag11210.pd2.repository;

import ag11210.pd2.dto.PlayerStatisticsDto;
import ag11210.pd2.model.PlayerEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<PlayerEntity, Long> {

    Optional<PlayerEntity> findByTeamAndNumber(String team, Integer number);

    @Query("select new ag11210.pd2.dto.PlayerStatisticsDto(" +
            "  p.firstName" +
            ", p.lastName" +
            ", p.team" +
            ", (select count(goals) from p.goals goals)" +
            ", (select count(assists) from p.assistedGoals assists))" +
            " from Player p" +
            " where (select count(goals) from p.goals goals) > 0" +
            " or (select count(assists) from p.assistedGoals assists) > 0" +
            " order by 4 desc, 5 desc, 1, 2, 3")
    List<PlayerStatisticsDto> getPlayerStatistics(Pageable pageable);
}
