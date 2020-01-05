package ag11210.pd2.repository;

import ag11210.pd2.dto.PlayerStatisticsDto;
import ag11210.pd2.model.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface PlayerRepository extends JpaRepository<PlayerEntity, Long> {

    Optional<PlayerEntity> findByTeamAndNumber(String team, Integer number);

    @Query("select new ag11210.pd2.dto.PlayerStatisticsDto(" +
            "  p.firstName" +
            ", p.lastName" +
            ", p.team" +
            ", p.goals.size" +
            ", p.assistedGoals.size)" +
            " from Player p order by 4 desc, 5 desc, 1, 2, 3")
    Stream<PlayerStatisticsDto> streamPlayerStatistics();
}
