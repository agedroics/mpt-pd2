package ag11210.pd2.dto;

import ag11210.pd2.configuration.BooleanAdapter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.Duration;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
public class GoalDto {

    @XmlAttribute(name = "Laiks")
    private Duration time = Duration.ZERO;

    @XmlAttribute(name = "Nr")
    private Integer playerNumber = 0;

    @XmlAttribute(name = "Sitiens")
    @XmlJavaTypeAdapter(value = BooleanAdapter.class)
    private Boolean penalty = false;

    @XmlElement(name = "P")
    private List<PlayerDto> assistingPlayers;
}
