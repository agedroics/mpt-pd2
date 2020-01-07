package ag11210.pd2.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity(name = "Player")
@Table(name = "player",
        uniqueConstraints = @UniqueConstraint(name = "player_uk", columnNames = {"team_id", "number"}))
public class PlayerEntity extends AbstractEntity {

    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "team_id", nullable = false,
            foreignKey = @ForeignKey(name = "player_team_id_fk"))
    private TeamEntity team;

    @Column(name = "number", nullable = false)
    private Integer number;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, columnDefinition = "char(1)")
    private PlayerRole role;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL)
    private Set<PlayerGameEntity> playerGames = new HashSet<>();
}
