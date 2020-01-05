package ag11210.pd2.repository;

import ag11210.pd2.model.RefereeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefereeRepository extends JpaRepository<RefereeEntity, Long> {

    Optional<RefereeEntity> findByFirstNameAndLastName(String firstName, String lastName);
}
