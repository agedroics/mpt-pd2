package ag11210.pd2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BasicPlayerStatisticsDto {

    private String firstName;
    private String lastName;
    private String team;
    private Integer number;
    private Long goals = 0L;
    private Long assists = 0L;
}
