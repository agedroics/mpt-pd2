package ag11210.pd2.controller;

import ag11210.pd2.dto.PlayerStatisticsDto;
import ag11210.pd2.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping(value = "api/players", produces = MediaType.APPLICATION_JSON_VALUE)
public class PlayerController {

    @Autowired
    private PlayerRepository playerRepository;

    @GetMapping("top10")
    @Transactional(readOnly = true)
    public List<PlayerStatisticsDto> getTop10Players() {
        try (Stream<PlayerStatisticsDto> statisticsStream = playerRepository.streamPlayerStatistics()) {
            return statisticsStream.limit(10).collect(Collectors.toList());
        }
    }
}
