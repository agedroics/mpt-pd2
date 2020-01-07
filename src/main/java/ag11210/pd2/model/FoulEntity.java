package ag11210.pd2.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Duration;

@Getter
@Setter
@Entity(name = "Foul")
@Table(name = "foul")
public class FoulEntity extends AbstractEntity {

    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "player_game_id", nullable = false,
            foreignKey = @ForeignKey(name = "foul_player_game_id_fk"))
    private PlayerGameEntity playerGame;

    @Column(name = "time", nullable = false)
    private Duration time;
}
