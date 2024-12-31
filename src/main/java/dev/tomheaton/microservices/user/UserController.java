package dev.tomheaton.microservices.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = this.userService.getUsers();

        return ResponseEntity.ok().body(users);
    }

    @PostMapping
    public ResponseEntity<Void> addUser(@RequestBody User user) {
        // TODO: return the created user
        this.userService.addUser(user);

        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = this.userService.getUser(id);

        return ResponseEntity.ok().body(user);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Void> updateUser(
        @PathVariable Long id,
        @RequestParam(required = false) String firstName,
        @RequestParam(required = false) String lastName,
        @RequestParam(required = false) String email,
        @RequestParam(required = false) String birthday,
        @RequestParam(required = false) String password
    ) {
        this.userService.updateUser(id, firstName, lastName, email, birthday, password);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        this.userService.deleteUser(id);

        return ResponseEntity.noContent().build();
    }
}
