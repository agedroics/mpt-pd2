package ag11210.pd2.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAttribute;

@Getter
@Setter
@EqualsAndHashCode
public class PlayerDto {

    @XmlAttribute(name = "Nr")
    protected Integer number;
}
