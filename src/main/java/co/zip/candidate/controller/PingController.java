package co.zip.candidate.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Endpoints for cloud computing providers such as AWS EC2 / ELB to check for application health and uptime.
 */
@RestController
@RequestMapping(value="/ping")
public class PingController {

    @GetMapping
    public String ping() {
        return "alive";
    }
}
