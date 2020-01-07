package ag11210.pd2.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Getter
@Setter
@Entity(name = "Referee")
@Table(name = "referee",
        uniqueConstraints = @UniqueConstraint(name = "referee_uk", columnNames = {"first_name", "last_name"}))
public class RefereeEntity extends AbstractEntity {

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;
}
