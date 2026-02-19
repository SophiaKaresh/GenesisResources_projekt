package cz.genesis.GenesisResources.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import cz.genesis.GenesisResources.dto.UserDto;
import cz.genesis.GenesisResources.model.User;
import cz.genesis.GenesisResources.repository.UserRepository;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private List<String> validPersonIds;

    @PostConstruct
    private void loadValidPersonIds() {
        try {
            validPersonIds = Files.readAllLines(
                    Paths.get(getClass().getClassLoader().getResource("dataPersonId.txt").toURI())
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to load personID list", e);
        }
    }

    public User createUser(User userInput) {

        if (!validPersonIds.contains(userInput.getPersonid())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid personID");
        }

        if (userRepository.findByPersonid(userInput.getPersonid()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "personID already exists");
        }

        userInput.setUuid(UUID.randomUUID().toString());

        return userRepository.save(userInput);
    }

    public User updateUser(User updatedUser) {

        User user = userRepository.findById(updatedUser.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        user.setName(updatedUser.getName());
        user.setSurname(updatedUser.getSurname());

        return userRepository.save(user);
    }

    public List<UserDto> getAllUsers(boolean detail) {

        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No users found");
        }

        return users.stream()
                .map(user -> detail
                                ? new UserDto(
                                user.getId(),
                                user.getName(),
                                user.getSurname(),
                                user.getPersonid(),
                                user.getUuid()
                        )
                                : new UserDto(
                                user.getId(),
                                user.getName(),
                                user.getSurname()
                        )
                )
                .toList();
    }

    public UserDto getUserById(Long id, boolean detail) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (detail) {
            return new UserDto(
                    user.getId(),
                    user.getName(),
                    user.getSurname(),
                    user.getPersonid(),
                    user.getUuid()
            );
        } else {
            return new UserDto(
                    user.getId(),
                    user.getName(),
                    user.getSurname()
            );
        }
    }

    public void deleteUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        userRepository.delete(user);
    }
}
