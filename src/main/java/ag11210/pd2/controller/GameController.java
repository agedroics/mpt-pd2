package ag11210.pd2.controller;

import ag11210.pd2.Utils;
import ag11210.pd2.dto.*;
import ag11210.pd2.model.*;
import ag11210.pd2.repository.GameRepository;
import ag11210.pd2.repository.PlayerRepository;
import ag11210.pd2.repository.RefereeRepository;
import ag11210.pd2.repository.TeamRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.JAXB;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/games", produces = MediaType.APPLICATION_JSON_VALUE)
public class GameController {

    private static final Predicate<String> xmlContentTypePredicate =
            Pattern.compile("^(?:text|application)/(?:.*\\+)?xml$", Pattern.CASE_INSENSITIVE).asMatchPredicate();

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private RefereeRepository refereeRepository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private TeamRepository teamRepository;

    @DeleteMapping
    @Transactional
    public void deleteGames() {
        gameRepository.deleteAll();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Transactional
    public void uploadGames(@RequestParam("file") MultipartFile[] files) {
        Map<Pair<String, Integer>, PlayerEntity> playerMap = playerRepository.findAll().stream()
                .collect(Utils.groupingById(player -> Pair.of(player.getTeam().getName(), player.getNumber())));
        Map<String, TeamEntity> teamMap = teamRepository.findAll().stream()
                .collect(Utils.groupingById(TeamEntity::getName));
        Map<Pair<String, String>, RefereeEntity> refereeMap = refereeRepository.findAll().stream()
                .collect(Utils.groupingById(referee -> Pair.of(referee.getFirstName(), referee.getLastName())));
        for (MultipartFile file : files) {
            GameDto gameDto = readFile(file);
            try {
                processGame(gameDto, playerMap, teamMap, refereeMap);
            } catch (RuntimeException e) {
                throw new RuntimeException("Kļūda apstrādājot failu " + file.getOriginalFilename() + ": " + e.getMessage());
            }
        }
    }

    private GameDto readFile(MultipartFile file) {
        String errorMessage;
        if (MediaType.APPLICATION_JSON_VALUE.equalsIgnoreCase(file.getContentType())) {
            try {
                return objectMapper.readValue(file.getInputStream(), GameDto.class);
            } catch (Exception e) {
                // Ja json nav UTF-8/UTF-16/UTF-32 kodējumā, tad Jackson metīs kļūdu, tāpēc mēģinām vēlreiz citā kodējumā.
                try {
                    String asCp1252 = new String(file.getBytes(), Charset.forName("Cp1252"));
                    return objectMapper.readValue(asCp1252, GameDto.class);
                } catch (Exception e1) {
                    errorMessage = e1.getMessage();
                }
            }
        } else if (file.getContentType() != null && xmlContentTypePredicate.test(file.getContentType())) {
            try {
                return JAXB.unmarshal(file.getInputStream(), GameDto.class);
            } catch (Exception e) {
                errorMessage = e.getMessage();
            }
        } else {
            errorMessage = "neatbalstīts formāts";
        }
        throw new RuntimeException("Neizdevās nolasīt failu " + file.getOriginalFilename() + ": " + errorMessage);
    }

    private void processGame(GameDto gameDto,
                             Map<Pair<String, Integer>, PlayerEntity> playerMap,
                             Map<String, TeamEntity> teamMap,
                             Map<Pair<String, String>, RefereeEntity> refereeMap) {
        for (TeamDto teamDto : gameDto.getTeams()) {
            if (gameRepository.existsByDateAndTeam(gameDto.getDate(), teamDto.getName())) {
                throw new RuntimeException("Komandas \"" + teamDto.getName() + "\" spēle datumā " + gameDto.getDate() + " jau ir ielasīta!");
            }
        }
        GameEntity game = new GameEntity();
        game.setDate(gameDto.getDate());
        game.setSpectators(gameDto.getSpectators());
        game.setLocation(gameDto.getLocation());
        game.setReferee(findOrCreateReferee(gameDto.getReferee(), refereeMap));
        if (gameDto.getAssistantReferees() != null) {
            gameDto.getAssistantReferees()
                    .forEach(assistantReferee -> game.getAssistantReferees()
                            .add(findOrCreateReferee(assistantReferee, refereeMap)));
        }
        gameDto.getTeams().forEach(team -> processTeam(team, game, playerMap, teamMap));
        gameRepository.save(game);
    }

    private void processTeam(TeamDto teamDto,
                             GameEntity game,
                             Map<Pair<String, Integer>, PlayerEntity> playerMap,
                             Map<String, TeamEntity> teamMap) {

        Set<Integer> starterNumbers = teamDto.getStarters().getStarters().stream()
                .map(PlayerDto::getNumber)
                .collect(Collectors.toSet());

        Map<Integer, PlayerGameEntity> playerGames = teamDto.getPlayers().getPlayers().stream()
                .map(playerInfoDto -> findOrCreatePlayer(playerInfoDto, teamDto.getName(), playerMap, teamMap))
                .map(player -> {
                    PlayerGameEntity playerGame = new PlayerGameEntity();
                    playerGame.setPlayer(player);
                    playerGame.setGame(game);
                    playerGame.setStarter(starterNumbers.contains(player.getNumber()));
                    return playerGame;
                })
                .peek(game.getPlayerGames()::add)
                .collect(Utils.groupingById(playerGame -> playerGame.getPlayer().getNumber()));

        if (teamDto.getSubstitutions() != null && teamDto.getSubstitutions().getSubstitutions() != null) {
            teamDto.getSubstitutions().getSubstitutions().forEach(substitutionDto -> {
                SubstitutionEntity substitution = new SubstitutionEntity();
                substitution.setTime(substitutionDto.getTime());
                PlayerGameEntity substitutedPlayerGame = playerGames.get(substitutionDto.getSubstitutedNumber());
                substitutedPlayerGame.getSubstitutions().add(substitution);
                PlayerGameEntity substitutePlayerGame = playerGames.get(substitutionDto.getSubstituteNumber());
                substitutePlayerGame.getSubstitutions().add(substitution);
                substitution.setPlayerGame(substitutedPlayerGame);
                substitution.setSubstitute(substitutePlayerGame);
            });
        }

        if (teamDto.getFouls() != null && teamDto.getFouls().getFouls() != null) {
            teamDto.getFouls().getFouls().forEach(foulDto -> {
                FoulEntity foul = new FoulEntity();
                foul.setTime(foulDto.getTime());
                PlayerGameEntity playerGame = playerGames.get(foulDto.getPlayerNumber());
                playerGame.getFouls().add(foul);
                foul.setPlayerGame(playerGame);
            });
        }

        if (teamDto.getGoals() != null && teamDto.getGoals().getGoals() != null) {
            teamDto.getGoals().getGoals().forEach(goalDto -> {
                GoalEntity goal = new GoalEntity();
                goal.setTime(goalDto.getTime());
                PlayerGameEntity playerGame = playerGames.get(goalDto.getPlayerNumber());
                playerGame.getGoals().add(goal);
                goal.setPlayerGame(playerGame);
                goal.setPenalty(goalDto.getPenalty());
                if (goalDto.getAssistingPlayers() != null) {
                    goalDto.getAssistingPlayers().stream()
                            .map(playerDto -> playerGames.get(playerDto.getNumber()))
                            .forEach(goal.getAssistingPlayerGames()::add);
                }
            });
        }
    }

    private TeamEntity findOrCreateTeam(String team, Map<String, TeamEntity> teamMap) {
        return teamMap.computeIfAbsent(team, teamName -> {
            TeamEntity teamEntity = new TeamEntity();
            teamEntity.setName(teamName);
            return teamEntity;
        });
    }

    private PlayerEntity findOrCreatePlayer(PlayerInfoDto playerInfoDto,
                                            String team,
                                            Map<Pair<String, Integer>, PlayerEntity> playerMap,
                                            Map<String, TeamEntity> teamMap) {
        return playerMap.computeIfAbsent(Pair.of(team, playerInfoDto.getNumber()), pair -> {
            PlayerEntity player = new PlayerEntity();
            player.setTeam(findOrCreateTeam(team, teamMap));
            player.setNumber(playerInfoDto.getNumber());
            player.setFirstName(playerInfoDto.getFirstName());
            player.setLastName(playerInfoDto.getLastName());
            player.setRole(playerInfoDto.getRole());
            return player;
        });
    }

    private RefereeEntity findOrCreateReferee(RefereeDto refereeDto,
                                              Map<Pair<String, String>, RefereeEntity> refereeMap) {
        return refereeMap.computeIfAbsent(Pair.of(refereeDto.getFirstName(), refereeDto.getLastName()), pair -> {
            RefereeEntity referee = new RefereeEntity();
            referee.setFirstName(refereeDto.getFirstName());
            referee.setLastName(refereeDto.getLastName());
            return referee;
        });
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleRuntimeException(RuntimeException e) {
        return e.getMessage();
    }
}
