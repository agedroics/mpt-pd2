package ag11210.pd2.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TournamentTableRowDto {

    private Long position = 1L;
    private String team;
    private Long wins = 0L;
    private Long overtimeWins = 0L;
    private Long losses = 0L;
    private Long overtimeLosses = 0L;
    private Long goalsFor = 0L;
    private Long goalsAgainst = 0L;
    private Long points = 0L;
}
