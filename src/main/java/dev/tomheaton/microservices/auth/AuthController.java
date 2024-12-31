package dev.tomheaton.microservices.auth;

import dev.tomheaton.microservices.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public void register(@RequestBody User user) {
        this.authService.registerUser(user);
    }

    @PostMapping("/login")
    public void login() {
        User user = new User();

        this.authService.loginUser(user);
    }

    @PostMapping("/logout")
    public void logout() {
        User user = new User();

        this.authService.logoutUser(user);
    }
}
