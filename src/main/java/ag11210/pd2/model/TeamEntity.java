package ag11210.pd2.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity(name = "Team")
@Table(name = "team",
        uniqueConstraints = @UniqueConstraint(name = "team_uk", columnNames = "name"))
public class TeamEntity extends AbstractEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "team")
    private Set<PlayerEntity> players = new HashSet<>();
}
