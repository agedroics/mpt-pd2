package ag11210.pd2.repository;

import ag11210.pd2.model.GoalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoalRepository extends JpaRepository<GoalEntity, Long> {
}
