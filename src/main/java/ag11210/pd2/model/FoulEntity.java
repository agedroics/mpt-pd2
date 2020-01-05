package ag11210.pd2.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Duration;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity(name = "Foul")
@Table(name = "fouls")
public class FoulEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private GameEntity game;

    @Column(nullable = false)
    private Duration time;

    @ManyToOne(optional = false)
    private PlayerEntity player;
}
