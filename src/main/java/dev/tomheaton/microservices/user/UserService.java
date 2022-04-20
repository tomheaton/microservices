package dev.tomheaton.microservices.user;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Service
public class UserService {

    public List<User> getUsers() {
        return List.of(
                new User(1L, "Tom", "Heaton", "tom@tomheaton.dev", LocalDate.of(2002, Month.SEPTEMBER, 30))
        );
    }
}
