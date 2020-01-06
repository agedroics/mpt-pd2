package ag11210.pd2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerStatisticsDto {

    private String firstName;
    private String lastName;
    private String team;
    private Long goals;
    private Long assists;
}
