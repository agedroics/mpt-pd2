package ag11210.pd2.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity(name = "Game")
@Table(name = "game")
public class GameEntity extends AbstractEntity {

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "spectators", nullable = false)
    private Long spectators;

    @Column(name = "location")
    private String location;

    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "referee_id", nullable = false,
            foreignKey = @ForeignKey(name = "game_referee_id_fk"))
    private RefereeEntity referee;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "game_assistant_referee",
            joinColumns = @JoinColumn(name = "game_id", nullable = false,
                    foreignKey = @ForeignKey(name = "game_assistant_referee_game_id_fk")),
            inverseJoinColumns = @JoinColumn(name = "referee_id", nullable = false,
                    foreignKey = @ForeignKey(name = "game_assistant_referee_referee_id_fk")))
    private Set<RefereeEntity> assistantReferees = new HashSet<>();

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private Set<PlayerGameEntity> playerGames = new HashSet<>();
}
