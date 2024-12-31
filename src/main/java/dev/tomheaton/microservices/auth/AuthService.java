package dev.tomheaton.microservices.auth;

import dev.tomheaton.microservices.user.User;
import dev.tomheaton.microservices.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;

    @Autowired
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(User user) {
        this.userRepository.save(user);
    }

    public void loginUser(User user) {
    }

    public void logoutUser(User user) {
    }
}
