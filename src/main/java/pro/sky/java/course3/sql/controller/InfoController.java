package pro.sky.java.course3.sql.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.java.course3.sql.service.InfoService;

@RestController
public class InfoController {

    private final InfoService infoService;

    public InfoController(InfoService infoService, ResponseEntity getPort) {
        this.infoService = infoService;
        this.getPort = getPort;
    }
}
