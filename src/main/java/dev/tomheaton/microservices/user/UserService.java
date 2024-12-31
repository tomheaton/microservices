package dev.tomheaton.microservices.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return this.userRepository.findAll();
    }

    public User getUser(Long userId) {
        return this.userRepository.findById(userId).orElseThrow(
            () -> new IllegalStateException("User with id " + userId + " does not exist.")
        );
    }

    public void addUser(User user) {
        Optional<User> userOptional = this.userRepository.findUserByEmail(user.getEmail());

        if (user.getFirstName() == null || user.getLastName() == null || user.getEmail() == null || user.getBirthday() == null || user.getPassword() == null) {
            throw new IllegalStateException("Invalid user object.");
        }

        if (userOptional.isPresent()) {
            throw new IllegalStateException("Email already exists.");
        }

        this.userRepository.save(user);
    }

    public User addUserWithReturn(User user) {
        Optional<User> userOptional = this.userRepository.findUserByEmail(user.getEmail());

        if (user.getFirstName() == null || user.getLastName() == null || user.getEmail() == null || user.getBirthday() == null || user.getPassword() == null) {
            throw new IllegalStateException("Invalid user object.");
        }

        if (userOptional.isPresent()) {
            throw new IllegalStateException("Email already exists.");
        }

        return this.userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        boolean exists = this.userRepository.existsById(userId);

        if (!exists) {
            throw new IllegalStateException("User with id " + userId + " does not exist.");
        }

        this.userRepository.deleteById(userId);
    }

    @Transactional
    public void updateUser(Long userId, String firstName, String lastName, String email, String birthday, String password) {
        User user = this.userRepository.findById(userId).orElseThrow(
            () -> new IllegalStateException("User with id " + userId + " does not exist.")
        );

        if (firstName != null && !firstName.isEmpty() && !user.getFirstName().equals(firstName)) {
            user.setFirstName(firstName);
        }

        if (lastName != null && !lastName.isEmpty() && !user.getLastName().equals(lastName)) {
            user.setLastName(lastName);
        }

        if (email != null && !email.isEmpty() && !user.getEmail().equals(email)) {
            Optional<User> userOptional = this.userRepository.findUserByEmail(email);

            if (userOptional.isPresent()) {
                throw new IllegalStateException("Email already exists.");
            }

            user.setEmail(email);
        }

        // TODO: implement birthday date parsing
//        if (birthday != null && !birthday.isEmpty() && !user.getBirthday().equals(birthday)) {
//            user.setBirthday(LocalDate.parse(birthday));
//        }

        if (password != null && !password.isEmpty() && !user.getPassword().equals(password)) {
            user.setPassword(password);
        }
    }
}
