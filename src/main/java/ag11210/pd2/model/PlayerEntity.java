package ag11210.pd2.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity(name = "Player")
@Table(name = "players", uniqueConstraints = @UniqueConstraint(columnNames = {"team", "number"}))
public class PlayerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "team", nullable = false)
    private String team;

    @Column(name = "number", nullable = false)
    private Integer number;

    private String firstName;
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "char(1)")
    private PlayerRole role;
}
