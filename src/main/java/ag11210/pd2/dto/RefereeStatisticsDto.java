package ag11210.pd2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefereeStatisticsDto {

    private String firstName;
    private String lastName;
    private Long games;
    private Long fouls;
    private Double averageFouls;
}
