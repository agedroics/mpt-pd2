package ag11210.pd2.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAttribute;
import java.time.Duration;

@Getter
@Setter
@EqualsAndHashCode
public class SubstitutionDto {

    @XmlAttribute(name = "Laiks")
    private Duration time;

    @XmlAttribute(name = "Nr1")
    private Integer substitutedPlayerNumber;

    @XmlAttribute(name = "Nr2")
    private Integer substitutePlayerNumber;
}
