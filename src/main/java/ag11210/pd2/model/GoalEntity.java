package ag11210.pd2.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity(name = "Goal")
@Table(name = "goals")
public class GoalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private GameEntity game;

    @Column(nullable = false)
    private Duration time;

    @ManyToOne(optional = false)
    private PlayerEntity player;

    @Column(nullable = false)
    private Boolean penalty;

    @ManyToMany
    private Set<PlayerEntity> assistingPlayers = new HashSet<>();
}
