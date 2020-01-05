package ag11210.pd2.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity(name = "Game")
@Table(name = "games")
public class GameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private Long spectators;

    private String location;

    @ManyToMany
    private Set<PlayerEntity> players = new HashSet<>();

    @ManyToMany
    private Set<PlayerEntity> starters = new HashSet<>();

    @ManyToOne
    private RefereeEntity referee;

    @ManyToMany
    private Set<RefereeEntity> assistantReferees = new HashSet<>();
}
