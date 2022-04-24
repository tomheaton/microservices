package dev.tomheaton.microservices.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class UserConfig {

    @Bean
    public CommandLineRunner commandLineRunner(UserRepository userRepository) {
        return args -> {
            User tom = new User("Tom", "Heaton", "tom@tomheaton.dev", LocalDate.of(2002, Month.SEPTEMBER, 30), "password");

            userRepository.saveAll(
                    List.of(tom)
            );
        };
    }
}
