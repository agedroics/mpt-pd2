package ag11210.pd2.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity(name = "Referee")
@Table(name = "referees", uniqueConstraints = @UniqueConstraint(columnNames = {"first_name", "last_name"}))
public class RefereeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;
}
