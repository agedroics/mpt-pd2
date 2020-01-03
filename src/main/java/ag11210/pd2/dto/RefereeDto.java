package ag11210.pd2.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAttribute;

@Getter
@Setter
@EqualsAndHashCode
public class RefereeDto {

    @XmlAttribute(name = "Vards")
    private String firstName;

    @XmlAttribute(name = "Uzvards")
    private String lastName;
}
