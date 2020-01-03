package ag11210.pd2.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAttribute;

@Getter
@Setter
@EqualsAndHashCode
public class PlayerInfoDto {

    @XmlAttribute(name = "Nr")
    protected Integer number;

    @XmlAttribute(name = "Vards")
    private String firstName;

    @XmlAttribute(name = "Uzvards")
    private String lastName;

    @XmlAttribute(name = "Loma")
    private Role role;

    public enum Role {
        V, A, U
    }
}
