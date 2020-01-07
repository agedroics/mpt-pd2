package ag11210.pd2.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Duration;

@Getter
@Setter
@Entity(name = "Substitution")
@Table(name = "substitution")
public class SubstitutionEntity extends AbstractEntity {

    @Column(name = "time", nullable = false)
    private Duration time;

    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "player_game_id", nullable = false,
            foreignKey = @ForeignKey(name = "substitution_player_game_id_fk"))
    private PlayerGameEntity playerGame;

    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "substitute_id", nullable = false,
            foreignKey = @ForeignKey(name = "substitution_substitute_id_fk"))
    private PlayerGameEntity substitute;
}
