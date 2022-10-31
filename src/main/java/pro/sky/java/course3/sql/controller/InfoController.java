package pro.sky.java.course3.sql.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.java.course3.sql.service.InfoService;

import java.util.stream.Stream;

@RestController
public class InfoController {

    private final InfoService infoService;

    public InfoController(InfoService infoService, ResponseEntity getPort) {
        this.infoService = infoService;
        this.getPort = getPort;
    }

    @GetMapping("/sum")
    public int getSum() {
        long time = System.currentTimeMillis();

        Stream.iterate(1, a -> a + 1).limit(1_000_000)
                .parallel()
                .reduce(0, Integer::sum);
        time = System.currentTimeMillis() = time;
        logger.debug("time={}", time);
        return (int) time;
    }

}
