package dev.tomheaton.microservices.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(this.userService.getUsers());
    }

    @PostMapping("/")
    public void addUser(@RequestBody User user) {
        this.userService.addUser(user);
    }


    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(this.userService.getUser(id));
    }

    @PostMapping("/")
    public ResponseEntity<User> addUserWithReturn(@RequestBody User user) {
        return new ResponseEntity<>(this.userService.addUserWithReturn(user), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public void updateUser(
        @PathVariable("id") Long id,
        @RequestParam(required = false) String firstName,
        @RequestParam(required = false) String lastName,
        @RequestParam(required = false) String email,
        @RequestParam(required = false) String birthday,
        @RequestParam(required = false) String password
    ) {
        this.userService.updateUser(id, firstName, lastName, email, birthday, password);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        this.userService.deleteUser(id);
    }
}
