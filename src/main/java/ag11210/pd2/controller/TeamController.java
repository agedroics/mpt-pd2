package ag11210.pd2.controller;

import ag11210.pd2.TimeInterval;
import ag11210.pd2.dto.BasicPlayerStatisticsDto;
import ag11210.pd2.dto.GoalkeeperStatisticsDto;
import ag11210.pd2.dto.PlayerStatisticsDto;
import ag11210.pd2.dto.TeamStatisticsDto;
import ag11210.pd2.model.*;
import ag11210.pd2.repository.PlayerRepository;
import ag11210.pd2.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.*;

@RestController
@RequestMapping(value = "api/teams", produces = MediaType.APPLICATION_JSON_VALUE)
public class TeamController {

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private PlayerRepository playerRepository;

    private static final Duration mainPeriodsDuration = Duration.of(1, ChronoUnit.HOURS);

    @GetMapping
    @Transactional(readOnly = true)
    public List<String> getTeams() {
        return teamRepository.getAllNames();
    }

    @GetMapping("{team}/statistics")
    @Transactional(readOnly = true)
    public TeamStatisticsDto getTeamStatistics(@PathVariable("team") String team) {
        Map<GameEntity, Duration> gameEndTimes = new HashMap<>();
        TeamStatisticsDto teamStatistics = new TeamStatisticsDto();

        for (PlayerEntity player : playerRepository.getForTeamStatistics(team)) {
            PlayerStatisticsDto playerStatistics;
            if (player.getRole() == PlayerRole.V) {
                playerStatistics = new GoalkeeperStatisticsDto();
                teamStatistics.getGoalkeepers().add((GoalkeeperStatisticsDto) playerStatistics);
            } else {
                playerStatistics = new PlayerStatisticsDto();
                teamStatistics.getPlayers().add(playerStatistics);
            }

            playerStatistics.setTeam(team);
            playerStatistics.setNumber(player.getNumber());
            playerStatistics.setFirstName(player.getFirstName());
            playerStatistics.setLastName(player.getLastName());

            for (PlayerGameEntity playerGame : player.getPlayerGames()) {
                List<Duration> onOffTimes = new ArrayList<>();
                playerGame.getSubstitutes().stream().map(SubstitutionEntity::getTime).forEach(onOffTimes::add);
                if (playerGame.getStarter()) {
                    onOffTimes.add(Duration.ZERO);
                    playerStatistics.setGamesAsStarter(playerStatistics.getGamesAsStarter() + 1);
                } else if (onOffTimes.isEmpty()) {
                    continue;
                }
                playerStatistics.setGames(playerStatistics.getGames() + 1);

                playerStatistics.setGoals(playerStatistics.getGoals() + playerGame.getGoals().size());
                playerStatistics.setAssists(playerStatistics.getAssists() + playerGame.getAssistedGoals().size());
                if (!playerGame.getFouls().isEmpty()) {
                    playerStatistics.setYellowCards(playerStatistics.getYellowCards() + 1);
                    if (playerGame.getFouls().size() > 1) {
                        playerStatistics.setRedCards(playerStatistics.getRedCards() + 1);
                    }
                }

                playerGame.getSubstitutions().stream()
                        .map(SubstitutionEntity::getTime)
                        .forEach(onOffTimes::add);
                playerGame.getFouls().stream()
                        .map(FoulEntity::getTime)
                        .sorted()
                        .skip(1)
                        .findFirst()
                        .ifPresent(onOffTimes::add);
                Duration gameEndTime = gameEndTimes.computeIfAbsent(playerGame.getGame(),
                        game -> game.getPlayerGames().stream()
                                .flatMap(playerGame2 -> playerGame2.getGoals().stream())
                                .map(GoalEntity::getTime)
                                .max(Duration::compareTo)
                                .map(time -> time.compareTo(mainPeriodsDuration) < 0 ? mainPeriodsDuration : time)
                                .get());
                onOffTimes.add(gameEndTime);
                onOffTimes.sort(Duration::compareTo);
                List<TimeInterval> activePeriods = new ArrayList<>();
                Duration temp = null;
                for (Duration onOffTime : onOffTimes) {
                    if (temp == null) {
                        temp = onOffTime;
                    } else {
                        activePeriods.add(new TimeInterval(temp, onOffTime));
                    }
                }
                activePeriods.stream()
                        .map(TimeInterval::getLength)
                        .reduce(Duration::plus)
                        .ifPresent(gameTimePlayed -> playerStatistics.setTimePlayed(playerStatistics.getTimePlayed().plus(gameTimePlayed)));

                if (playerStatistics instanceof GoalkeeperStatisticsDto) {
                    long goalsConceded = playerGame.getGame().getPlayerGames().stream()
                            .filter(playerGame2 -> !playerGame2.getPlayer().getTeam().getName().equals(team))
                            .flatMap(playerGame2 -> playerGame2.getGoals().stream())
                            .filter(goal -> activePeriods.stream().anyMatch(activePeriod -> activePeriod.includes(goal.getTime())))
                            .count();
                    GoalkeeperStatisticsDto goalkeeperStatistics = (GoalkeeperStatisticsDto) playerStatistics;
                    goalkeeperStatistics.setGoalsConceded(goalkeeperStatistics.getGoalsConceded() + goalsConceded);
                }
            }

            if (playerStatistics instanceof GoalkeeperStatisticsDto && playerStatistics.getGames() > 0) {
                GoalkeeperStatisticsDto goalkeeperStatistics = (GoalkeeperStatisticsDto) playerStatistics;
                goalkeeperStatistics.setAverageGoalsConceded(goalkeeperStatistics.getGoalsConceded().doubleValue() / goalkeeperStatistics.getGames());
            }
        }

        teamStatistics.getPlayers().sort(Comparator.comparing(BasicPlayerStatisticsDto::getGoals).reversed()
                .thenComparing(BasicPlayerStatisticsDto::getAssists, Comparator.reverseOrder()));
        teamStatistics.getGoalkeepers().sort(Comparator.comparing(GoalkeeperStatisticsDto::getAverageGoalsConceded)
                .thenComparing(GoalkeeperStatisticsDto::getGoalsConceded));
        return teamStatistics;
    }
}
