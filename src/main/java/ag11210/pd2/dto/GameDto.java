package ag11210.pd2.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@XmlRootElement(name = "Spele")
public class GameDto {

    @XmlAttribute(name = "Laiks")
    private LocalDate date;

    @XmlAttribute(name = "Skatitaji")
    private Integer spectators;

    @XmlAttribute(name = "Vieta")
    private String location;

    @XmlElement(name = "Komanda")
    private List<TeamDto> teams;

    @XmlElement(name = "VT")
    private RefereeDto referee;

    @XmlElement(name = "T")
    private List<RefereeDto> assistantReferees;
}
