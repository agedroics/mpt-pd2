package ag11210.pd2.controller;

import ag11210.pd2.dto.GameDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "games", consumes = {
        MediaType.APPLICATION_XML_VALUE,
        MediaType.APPLICATION_JSON_VALUE})
public class GameController {

    @PostMapping
    public void uploadGame(@RequestBody GameDto game) {
        System.out.println(game);
    }
}
