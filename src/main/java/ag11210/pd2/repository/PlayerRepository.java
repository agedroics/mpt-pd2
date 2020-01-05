package ag11210.pd2.repository;

import ag11210.pd2.model.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<PlayerEntity, Long> {

    Optional<PlayerEntity> findByTeamAndNumber(String team, Integer number);
}
