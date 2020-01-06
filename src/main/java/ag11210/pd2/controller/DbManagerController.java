package ag11210.pd2.controller;

import org.hsqldb.util.DatabaseManagerSwing;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;

@RestController
@RequestMapping("api/open-db-manager")
public class DbManagerController {

    @PostMapping
    public void openDbManager() {
        try {
            if (Desktop.isDesktopSupported()) {
                DatabaseManagerSwing.main(new String[]{"-noexit", "-url", "jdbc:hsqldb:file:data/", "-user", "sa"});
            }
        } catch (Exception e) {
            // do nothing
        }
    }
}
