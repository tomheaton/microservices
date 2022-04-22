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
        return userRepository.findAll();
    }

    public void addNewUser(User user) {
        Optional<User> userOptional = userRepository.findUserByEmail(user.getEmail());

        if (userOptional.isPresent()) {
            throw new IllegalStateException("Email already exists.");
        }

        userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        boolean exists = userRepository.existsById(userId);

        if (!exists) {
            throw new IllegalStateException("User with id " + userId + " does not exist.");
        }

        userRepository.deleteById(userId);
    }

    @Transactional
    public void updateStudent(Long userId, String firstName, String lastName, String email, String birthday) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("User with id " + userId + " does not exist.")
        );

        if (firstName != null && firstName.length() > 0 && !user.getFirstName().equals(firstName)) {
            user.setFirstName(firstName);
        }

        if (lastName != null && lastName.length() > 0 && !user.getLastName().equals(lastName)) {
            user.setLastName(lastName);
        }

        if (email != null && email.length() > 0 && !user.getEmail().equals(email)) {
            Optional<User> userOptional = userRepository.findUserByEmail(email);

            if (userOptional.isPresent()) {
                throw new IllegalStateException("Email already exists.");
            }

            user.setEmail(email);
        }

        // TODO: fix date parsing.
        /*if (birthday != null && birthday.length() > 0 && !user.getBirthday().equals(birthday)) {
            user.setBirthday(LocalDate.parse(birthday));
        }*/
    }

    public User getUser(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);

//        if (userOptional.isEmpty()) {
//            throw new IllegalStateException("User with id " + userId + " does not exist.");
//        }

        if (userOptional.isEmpty()) {
            return null;
        }

        return userOptional.get();
    }
}
