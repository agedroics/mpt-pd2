package ag11210.pd2.controller;

import org.hsqldb.util.DatabaseManagerSwing;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;

@RestController
@RequestMapping("api/database")
public class DatabaseController {

    @PostMapping("open-manager")
    public void openDatabaseManager() {
        try {
            if (Desktop.isDesktopSupported()) {
                DatabaseManagerSwing.main(new String[]{"-noexit", "-url", "jdbc:hsqldb:file:data/", "-user", "sa"});
            }
        } catch (Exception | Error e) {
            // do nothing
        }
    }
}
