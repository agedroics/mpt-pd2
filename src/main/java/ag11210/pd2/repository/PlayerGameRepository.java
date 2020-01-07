package ag11210.pd2.repository;

import ag11210.pd2.model.PlayerGameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerGameRepository extends JpaRepository<PlayerGameEntity, Long> {
}
