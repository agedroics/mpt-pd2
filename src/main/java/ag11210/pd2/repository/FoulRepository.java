package ag11210.pd2.repository;

import ag11210.pd2.model.FoulEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoulRepository extends JpaRepository<FoulEntity, Long> {
}
