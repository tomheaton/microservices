package dev.tomheaton.microservices.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> getUsers() {
        return this.repository.findAll();
    }

    public User getUser(Long id) {
        return this.repository.findById(id).orElseThrow(
            () -> new IllegalStateException("User with id " + id + " does not exist.")
        );
    }

    public User addUser(User user) {
        if (user.getFirstName() == null || user.getLastName() == null || user.getEmail() == null || user.getBirthday() == null || user.getPassword() == null) {
            throw new IllegalStateException("Invalid user object.");
        }

        Optional<User> userOptional = this.repository.findUserByEmail(user.getEmail());

        if (userOptional.isPresent()) {
            throw new IllegalStateException("Email already exists.");
        }

        return this.repository.save(user);
    }

    @Transactional
    public User updateUser(Long id, User user) {
        User existingUser = this.repository.findById(id).orElseThrow(
            () -> new IllegalStateException("User with id " + id + " does not exist.")
        );

        if (user.getFirstName() != null) {
            existingUser.setFirstName(user.getFirstName());
        }

        if (user.getLastName() != null) {
            existingUser.setLastName(user.getLastName());
        }

        if (user.getEmail() != null) {
            Optional<User> userOptional = this.repository.findUserByEmail(user.getEmail());

            if (userOptional.isPresent()) {
                throw new IllegalStateException("Email already exists.");
            }

            existingUser.setEmail(user.getEmail());
        }

        if (user.getBirthday() != null) {
            existingUser.setBirthday(user.getBirthday());
        }

        if (user.getPassword() != null) {
            existingUser.setPassword(user.getPassword());
        }

        return this.repository.save(existingUser);
    }

    @Transactional
    public User replaceUser(Long id, User newUser) {
        return this.repository.findById(id).map(user -> {
            if (newUser.getFirstName() != null) {
                user.setFirstName(newUser.getFirstName());
            }

            if (newUser.getLastName() != null) {
                user.setLastName(newUser.getLastName());
            }

            if (newUser.getEmail() != null) {
                Optional<User> userOptional = this.repository.findUserByEmail(newUser.getEmail());

                if (userOptional.isPresent()) {
                    throw new IllegalStateException("Email already exists.");
                }

                user.setEmail(newUser.getEmail());
            }

            if (newUser.getBirthday() != null) {
                user.setBirthday(newUser.getBirthday());
            }

            if (newUser.getPassword() != null) {
                user.setPassword(newUser.getPassword());
            }

            return this.repository.save(user);
        }).orElseGet(() -> {
            newUser.setId(id);

            return this.repository.save(newUser);
        });
    }

    public void deleteUser(Long id) {
        boolean exists = this.repository.existsById(id);

        if (!exists) {
            throw new IllegalStateException("User with id " + id + " does not exist.");
        }

        this.repository.deleteById(id);
    }
}
