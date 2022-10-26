package pro.sky.java.course3.sql.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class InfoService {

    @Value("${server.port}")
    private String serverPort;

    @Autowired
    RestTemplate restTemplate;


    public String getServerPort() {
        return serverPort;
    }
}
