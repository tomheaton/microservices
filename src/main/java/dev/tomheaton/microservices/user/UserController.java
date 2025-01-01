package dev.tomheaton.microservices.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService service;
    private final Logger logger;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
        this.logger = LoggerFactory.getLogger(UserController.class);
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = this.service.getUsers();

        return ResponseEntity.ok().body(users);
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User addedUser = this.service.addUser(user);

        return ResponseEntity.ok().body(addedUser);
    }


    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = this.service.getUser(id);

        return ResponseEntity.ok().body(user);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        User updatedUser = this.service.updateUser(id, user);

        return ResponseEntity.ok().body(updatedUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> replaceUser(@PathVariable Long id, @RequestBody User user) {
        User replacedUser = this.service.replaceUser(id, user);

        return ResponseEntity.ok().body(replacedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        this.service.deleteUser(id);

        return ResponseEntity.noContent().build();
    }
}
