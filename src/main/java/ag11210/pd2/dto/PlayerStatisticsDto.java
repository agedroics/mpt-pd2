package ag11210.pd2.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Duration;

@Getter
@Setter
public class PlayerStatisticsDto extends BasicPlayerStatisticsDto {

    private Long games = 0L;
    private Long gamesAsStarter = 0L;
    private Duration timePlayed = Duration.ZERO;
    private Long yellowCards = 0L;
    private Long redCards = 0L;
}
