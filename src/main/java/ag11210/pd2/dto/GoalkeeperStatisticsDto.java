package ag11210.pd2.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoalkeeperStatisticsDto extends PlayerStatisticsDto {

    private Long goalsConceded = 0L;
    private Double averageGoalsConceded = 0.;
}
