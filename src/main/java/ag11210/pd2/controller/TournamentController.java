package ag11210.pd2.controller;

import ag11210.pd2.dto.TournamentTableRowDto;
import ag11210.pd2.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/tournament", produces = MediaType.APPLICATION_JSON_VALUE)
public class TournamentController {

    @Autowired
    private GameRepository gameRepository;

    @GetMapping
    @Transactional(readOnly = true)
    public List<TournamentTableRowDto> getTournamentTable() {
        Map<String, TournamentTableRowDto> teamStatistics = new HashMap<>();
        Function<String, TournamentTableRowDto> statisticsGetter = team ->
                teamStatistics.computeIfAbsent(team, teamName -> {
                    TournamentTableRowDto row = new TournamentTableRowDto();
                    row.setTeam(teamName);
                    return row;
                });

        gameRepository.findAll().forEach(game -> {
            boolean overtime = game.getGoals().stream()
                    .anyMatch(goal -> goal.getTime().compareTo(Duration.of(60, ChronoUnit.MINUTES)) > 0);
            Map<String, Long> teamGoals = game.getGoals().stream()
                    .collect(Collectors.groupingBy(goal -> goal.getPlayer().getTeam(), Collectors.counting()));
            String winningTeam = teamGoals.entrySet().stream()
                    .max(Comparator.comparingLong(Map.Entry::getValue))
                    .map(Map.Entry::getKey)
                    .get();
            teamGoals.forEach((team, goals) -> {
                TournamentTableRowDto statistics = statisticsGetter.apply(team);
                statistics.setGoalsFor(statistics.getGoalsFor() + goals);
                teamGoals.keySet().stream()
                        .filter(Predicate.not(Predicate.isEqual(team)))
                        .forEach(otherTeam -> {
                            TournamentTableRowDto otherTeamStatistics = statisticsGetter.apply(otherTeam);
                            otherTeamStatistics.setGoalsAgainst(otherTeamStatistics.getGoalsAgainst() + goals);
                        });
                if (team.equals(winningTeam)) {
                    statistics.setWins(statistics.getWins() + 1);
                    if (overtime) {
                        statistics.setOvertimeWins(statistics.getOvertimeWins() + 1);
                        statistics.setPoints(statistics.getPoints() + 3);
                    } else {
                        statistics.setPoints(statistics.getPoints() + 5);
                    }
                } else {
                    statistics.setLosses(statistics.getLosses() + 1);
                    if (overtime) {
                        statistics.setOvertimeLosses(statistics.getOvertimeLosses() + 1);
                        statistics.setPoints(statistics.getPoints() + 2);
                    } else {
                        statistics.setPoints(statistics.getPoints() + 1);
                    }
                }
            });
        });

        List<TournamentTableRowDto> result = new ArrayList<>();
        teamStatistics.values().stream()
                .sorted(Comparator.comparingLong(TournamentTableRowDto::getPoints).reversed()
                        .thenComparing(TournamentTableRowDto::getTeam, String.CASE_INSENSITIVE_ORDER))
                .forEachOrdered(row -> {
                    if (!result.isEmpty()) {
                        TournamentTableRowDto previous = result.get(result.size() - 1);
                        row.setPosition(previous.getPosition() + (previous.getPoints() > row.getPoints() ? 1 : 0));
                    }
                    result.add(row);
                });
        return result;
    }
}
