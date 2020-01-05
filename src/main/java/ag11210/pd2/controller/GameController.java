package ag11210.pd2.controller;

import ag11210.pd2.dto.GameDto;
import ag11210.pd2.dto.PlayerInfoDto;
import ag11210.pd2.dto.RefereeDto;
import ag11210.pd2.dto.TeamDto;
import ag11210.pd2.mapper.GameMapper;
import ag11210.pd2.mapper.PlayerMapper;
import ag11210.pd2.mapper.RefereeMapper;
import ag11210.pd2.model.*;
import ag11210.pd2.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.JAXB;
import java.nio.charset.Charset;

@RestController
@RequestMapping(path = "games", produces = MediaType.APPLICATION_JSON_VALUE)
public class GameController {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private RefereeRepository refereeRepository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private SubstitutionRepository substitutionRepository;
    @Autowired
    private FoulRepository foulRepository;
    @Autowired
    private GoalRepository goalRepository;
    @Autowired
    private GameMapper gameMapper;
    @Autowired
    private RefereeMapper refereeMapper;
    @Autowired
    private PlayerMapper playerMapper;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Transactional
    public void uploadGames(@RequestParam("file") MultipartFile[] files) {
        for (MultipartFile file : files) {
            GameDto gameDto = readFile(file);
            try {
                processGame(gameDto);
            } catch (RuntimeException e) {
                throw new RuntimeException("Kļūda apstrādājot failu " + file.getOriginalFilename() + ": " + e.getMessage());
            }
        }
    }

    private GameDto readFile(MultipartFile file) {
        String cp1252 = null;
        try {
            return objectMapper.readValue(file.getInputStream(), GameDto.class);
        } catch (Exception e) {
            try {
                cp1252 = new String(file.getBytes(), Charset.forName("Cp1252"));
                return objectMapper.readValue(cp1252, GameDto.class);
            } catch (Exception e1) {
                // do nothing
            }
        }
        try {
            return JAXB.unmarshal(file.getInputStream(), GameDto.class);
        } catch (Exception e) {
            try {
                return JAXB.unmarshal(cp1252, GameDto.class);
            } catch (Exception e1) {
                // do nothing
            }
            throw new RuntimeException("Neizdevās nolasīt failu " + file.getOriginalFilename());
        }
    }

    private void processGame(GameDto gameDto) {
        for (TeamDto teamDto : gameDto.getTeams()) {
            if (gameRepository.findByDateAndTeam(gameDto.getDate(), teamDto.getName()).isPresent()) {
                throw new RuntimeException("Komandas \"" + teamDto.getName() + "\" spēle datumā " + gameDto.getDate() + " jau ir ielasīta!");
            }
        }
        GameEntity game = gameMapper.dtoToEntity(gameDto);
        game.setReferee(findOrCreateReferee(gameDto.getReferee()));
        gameRepository.save(game);
        if (gameDto.getTeams() != null) {
            gameDto.getTeams().forEach(team -> processTeam(team, game));
        }
        if (gameDto.getAssistantReferees() != null) {
            gameDto.getAssistantReferees()
                    .forEach(assistantReferee -> game.getAssistantReferees().add(findOrCreateReferee(assistantReferee)));
        }
    }

    private void processTeam(TeamDto teamDto, GameEntity game) {
        teamDto.getPlayers().getPlayers()
                .forEach(playerInfoDto -> game.getPlayers().add(findOrCreatePlayer(playerInfoDto, teamDto.getName())));

        if (teamDto.getSubstitutions() != null && teamDto.getSubstitutions().getSubstitutions() != null) {
            teamDto.getSubstitutions().getSubstitutions().forEach(substitutionDto -> {
                SubstitutionEntity substitution = new SubstitutionEntity();
                substitution.setGame(game);
                substitution.setTime(substitutionDto.getTime());
                playerRepository.findByTeamAndNumber(teamDto.getName(), substitutionDto.getSubstitutedNumber())
                        .ifPresent(substitution::setSubstitutedPlayer);
                playerRepository.findByTeamAndNumber(teamDto.getName(), substitutionDto.getSubstituteNumber())
                        .ifPresent(substitution::setSubstitute);
                substitutionRepository.save(substitution);
            });
        }

        teamDto.getStarters().getStarters()
                .forEach(playerDto -> playerRepository.findByTeamAndNumber(teamDto.getName(), playerDto.getNumber())
                        .ifPresent(game.getStarters()::add));

        if (teamDto.getFouls() != null && teamDto.getFouls().getFouls() != null) {
            teamDto.getFouls().getFouls().forEach(foulDto -> {
                FoulEntity foul = new FoulEntity();
                foul.setGame(game);
                foul.setTime(foulDto.getTime());
                playerRepository.findByTeamAndNumber(teamDto.getName(), foulDto.getPlayerNumber())
                        .ifPresent(foul::setPlayer);
                foulRepository.save(foul);
            });
        }

        if (teamDto.getGoals() != null && teamDto.getGoals().getGoals() != null) {
            teamDto.getGoals().getGoals().forEach(goalDto -> {
                GoalEntity goal = new GoalEntity();
                goal.setGame(game);
                goal.setTime(goalDto.getTime());
                playerRepository.findByTeamAndNumber(teamDto.getName(), goalDto.getPlayerNumber())
                        .ifPresent(goal::setPlayer);
                goal.setPenalty(goalDto.getPenalty());
                goalRepository.save(goal);
                if (goalDto.getAssistingPlayers() != null) {
                    goalDto.getAssistingPlayers()
                            .forEach(playerDto -> playerRepository.findByTeamAndNumber(teamDto.getName(), playerDto.getNumber())
                                    .ifPresent(goal.getAssistingPlayers()::add));
                }
            });
        }
    }

    private PlayerEntity findOrCreatePlayer(PlayerInfoDto playerInfoDto, String team) {
        return playerRepository.findByTeamAndNumber(team, playerInfoDto.getNumber())
                .orElseGet(() -> {
                    PlayerEntity player = playerMapper.dtoToEntity(playerInfoDto);
                    player.setTeam(team);
                    return playerRepository.save(player);
                });
    }

    private RefereeEntity findOrCreateReferee(RefereeDto refereeDto) {
        return refereeRepository.findByFirstNameAndLastName(refereeDto.getFirstName(), refereeDto.getLastName())
                .orElseGet(() -> {
                    RefereeEntity referee = refereeMapper.dtoToEntity(refereeDto);
                    return refereeRepository.save(referee);
                });
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleRuntimeException(RuntimeException e) {
        return e.getMessage();
    }
}
