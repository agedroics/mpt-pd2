package ag11210.pd2.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity(name = "Goal")
@Table(name = "goal")
public class GoalEntity extends AbstractEntity {

    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "player_game_id", nullable = false,
            foreignKey = @ForeignKey(name = "goal_player_game_id_fk"))
    private PlayerGameEntity playerGame;

    @Column(name = "time", nullable = false)
    private Duration time;

    @Column(name = "penalty", nullable = false)
    private Boolean penalty;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "goal_assisting_player_game",
            joinColumns = @JoinColumn(name = "goal_id", nullable = false,
                    foreignKey = @ForeignKey(name = "goal_assisting_player_game_goal_id_fk")),
            inverseJoinColumns = @JoinColumn(name = "player_game_id", nullable = false,
                    foreignKey = @ForeignKey(name = "goal_assisting_player_game_player_game_id_fk")))
    private Set<PlayerGameEntity> assistingPlayerGames = new HashSet<>();
}
