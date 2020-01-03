package ag11210.pd2.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.time.Duration;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
public class GoalDto {

    @XmlAttribute(name = "Laiks")
    private Duration time;

    @XmlAttribute(name = "Nr")
    private Integer playerNumber;

    @XmlAttribute(name = "Sitiens")
    private Boolean penalty;

    @XmlElement(name = "P")
    private List<PlayerDto> assistingPlayers;
}
