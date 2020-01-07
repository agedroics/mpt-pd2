package ag11210.pd2.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity(name = "PlayerGame")
@Table(name = "player_game",
        uniqueConstraints = @UniqueConstraint(name = "player_game_uk", columnNames = {"player_id", "game_id"}))
public class PlayerGameEntity extends AbstractEntity {

    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "player_id", nullable = false,
            foreignKey = @ForeignKey(name = "player_game_player_id_fk"))
    private PlayerEntity player;

    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "game_id", nullable = false,
            foreignKey = @ForeignKey(name = "player_game_game_id_fk"))
    private GameEntity game;

    @Column(name = "starter", nullable = false)
    private Boolean starter;

    @OneToMany(mappedBy = "playerGame", cascade = CascadeType.ALL)
    private Set<FoulEntity> fouls = new HashSet<>();

    @OneToMany(mappedBy = "playerGame", cascade = CascadeType.ALL)
    private Set<GoalEntity> goals = new HashSet<>();

    @ManyToMany(mappedBy = "assistingPlayerGames", cascade = CascadeType.ALL)
    private Set<GoalEntity> assistedGoals = new HashSet<>();

    @OneToMany(mappedBy = "playerGame", cascade = CascadeType.ALL)
    private Set<SubstitutionEntity> substitutions = new HashSet<>();

    @OneToMany(mappedBy = "substitute", cascade = CascadeType.ALL)
    private Set<SubstitutionEntity> substitutes = new HashSet<>();
}
