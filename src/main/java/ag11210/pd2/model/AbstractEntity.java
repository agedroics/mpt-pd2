package ag11210.pd2.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    @Column(columnDefinition = "UUID")
    private UUID id;

    @PrePersist
    private void prepareId() {
        if (id == null) {
            ThreadLocalRandom random = ThreadLocalRandom.current();
            id = new UUID(random.nextLong(), random.nextLong());
        }
    }

    @Override
    public int hashCode() {
        prepareId();
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AbstractEntity)) {
            return false;
        }
        AbstractEntity other = (AbstractEntity) obj;
        prepareId();
        other.prepareId();
        return id.equals(other.id);
    }
}
