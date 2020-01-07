package ag11210.pd2.dto;

import ag11210.pd2.configuration.LocalDateAdapter;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@XmlRootElement(name = "Spele")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@JsonTypeName("Spele")
public class GameDto {

    @XmlAttribute(name = "Laiks", required = true)
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    private LocalDate date;

    @XmlAttribute(name = "Skatitaji")
    private Long spectators = 0L;

    @XmlAttribute(name = "Vieta")
    private String location;

    @XmlElement(name = "Komanda", required = true)
    private List<TeamDto> teams;

    @XmlElement(name = "VT", required = true)
    private RefereeDto referee;

    @XmlElement(name = "T")
    private List<RefereeDto> assistantReferees;
}
