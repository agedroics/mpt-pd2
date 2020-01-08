package ag11210.pd2.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TeamStatisticsDto {

    private List<PlayerStatisticsDto> players = new ArrayList<>();
    private List<GoalkeeperStatisticsDto> goalkeepers = new ArrayList<>();
}
