package com.rjb.hobby_tracker.users;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    /**
     * Constructor for user controller.
     * 
     * @param userService
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Return a list of users.
     * 
     * @return
     */
    @GetMapping(path="/list")
    public List<UserDTO> listUsers() {
        return userService.findAll();
    }

    /**
     * Find a user by username. 
     * 
     * @param username
     * @return
     */
    @GetMapping(path="/get")
    public UserDTO getUser (final String username) {
        Optional<UserDTO> res = userService.findByUsername(username);

        if (res.isPresent()) {
            return res.get();
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User " + username + " not found");
    }

    /**
     * Create a new user.
     * 
     * @param newUser
     * @return status code 201
     */
    @PostMapping()
    public ResponseEntity<String> postMethodName(@RequestBody UserDTO newUser) {
        userService.createUser(newUser);
        return new ResponseEntity<>("User created successfully.", HttpStatus.CREATED);
    }
    
}
