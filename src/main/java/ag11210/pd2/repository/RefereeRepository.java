package ag11210.pd2.repository;

import ag11210.pd2.dto.RefereeStatisticsDto;
import ag11210.pd2.model.RefereeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefereeRepository extends JpaRepository<RefereeEntity, Long> {

    @Query("select new ag11210.pd2.dto.RefereeStatisticsDto(" +
            "  r.firstName," +
            "  r.lastName," +
            "  count(distinct g)," +
            "  count(f)," +
            "  case when count(distinct g) = 0 then 0. else (count(f) * 1. / count(distinct g)) end)" +
            " from Referee r" +
            " left join r.games g" +
            " left join g.playerGames pg" +
            " left join pg.fouls f" +
            " group by r" +
            " order by 5 desc, 1, 2")
    List<RefereeStatisticsDto> getRefereeStatistics();
}
