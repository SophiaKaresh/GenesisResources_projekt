package cz.genesis.GenesisResources.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import cz.genesis.GenesisResources.dto.UserDto;
import cz.genesis.GenesisResources.model.User;
import cz.genesis.GenesisResources.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public User createUser(@RequestBody User userInput) {
        return userService.createUser(userInput);
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @GetMapping
    public List<UserDto> getAllUsers(
            @RequestParam(required = false, defaultValue = "false") boolean detail
    ) {
        return userService.getAllUsers(detail);
    }

    @GetMapping("/{id}")
    public UserDto getUserById(
            @PathVariable Long id,
            @RequestParam(required = false, defaultValue = "false") boolean detail
    ) {
        return userService.getUserById(id, detail);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
    }
}