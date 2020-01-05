package ag11210.pd2.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
public class TeamDto {

    @XmlAttribute(name = "Nosaukums", required = true)
    private String name;

    /*
        Parasti šeit nevajadzētu veidot wrapper klases un pietiktu ar @XmlElementWrapper anotāciju, bet diemžēl
        pašreizējai Jackson versijai šī funkcionalitāte nestrādā.
        https://github.com/FasterXML/jackson-modules-base/issues/45
     */
    @XmlElement(name = "Speletaji", required = true)
    private PlayersDto players;

    @XmlElement(name = "Mainas")
    private SubstitutionsDto substitutions;

    @XmlElement(name = "Pamatsastavs", required = true)
    private StartersDto starters;

    @XmlElement(name = "Sodi")
    private FoulsDto fouls;

    @XmlElement(name = "Varti")
    private GoalsDto goals;

    @Getter
    @Setter
    @EqualsAndHashCode
    public static class PlayersDto {

        @XmlElement(name = "Speletajs", required = true)
        private List<PlayerInfoDto> players;
    }

    @Getter
    @Setter
    @EqualsAndHashCode
    public static class SubstitutionsDto {

        @XmlElement(name = "Maina")
        private List<SubstitutionDto> substitutions;
    }

    @Getter
    @Setter
    @EqualsAndHashCode
    public static class StartersDto {

        @XmlElement(name = "Speletajs", required = true)
        private List<PlayerDto> starters;
    }

    @Getter
    @Setter
    @EqualsAndHashCode
    public static class FoulsDto {

        @XmlElement(name = "Sods")
        private List<FoulDto> fouls;
    }

    @Getter
    @Setter
    @EqualsAndHashCode
    public static class GoalsDto {

        @XmlElement(name = "VG")
        private List<GoalDto> goals;
    }
}
