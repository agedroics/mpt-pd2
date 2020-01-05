package ag11210.pd2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("api/shutdown")
public class ShutdownController {

    @Autowired
    private ApplicationContext ctx;

    @PostMapping
    public void shutdown(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        response.getOutputStream().flush();
        SpringApplication.exit(ctx, () -> 0);
        System.exit(0);
    }
}
