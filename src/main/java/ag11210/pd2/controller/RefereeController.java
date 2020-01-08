package ag11210.pd2.controller;

import ag11210.pd2.dto.RefereeStatisticsDto;
import ag11210.pd2.repository.RefereeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "api/referees", produces = MediaType.APPLICATION_JSON_VALUE)
public class RefereeController {

    @Autowired
    private RefereeRepository refereeRepository;

    @GetMapping("statistics")
    @Transactional(readOnly = true)
    public List<RefereeStatisticsDto> getRefereeStatistics() {
        return refereeRepository.getRefereeStatistics();
    }
}
