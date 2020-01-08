package ag11210.pd2.repository;

import ag11210.pd2.model.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<TeamEntity, Long> {

    @Query("select t.name from Team t order by t.name")
    List<String> getAllNames();
}
