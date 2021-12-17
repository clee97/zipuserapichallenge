package co.zip.candidate.userapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {
    "co.zip.candidate.filter",
    "co.zip.candidate.exception",
    "co.zip.candidate.controller",
    "co.zip.candidate.service",
    "co.zip.candidate.repository"
})
@EnableJpaRepositories("co.zip.candidate.repository")
@EntityScan("co.zip.candidate.model.entity")
public class UserApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApiApplication.class, args);
    }

}
