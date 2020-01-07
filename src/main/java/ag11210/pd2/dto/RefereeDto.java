package ag11210.pd2.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAttribute;

@Getter
@Setter
@EqualsAndHashCode
public class RefereeDto {

    @XmlAttribute(name = "Vards", required = true)
    private String firstName;

    @XmlAttribute(name = "Uzvards", required = true)
    private String lastName;
}
