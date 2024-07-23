package com.rjb.hobby_tracker.users;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;



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
     * @return List of UserDTOs
     */
    @GetMapping(path="/list")
    public List<UserDTO> listUsers() {
        return userService.findAll();
    }

    /**
     * Find a user by username. 
     * 
     * @param username
     * @return UserDTO
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
    public ResponseEntity<String> addUser(@RequestBody UserDTO newUser) {
        userService.createUser(newUser);
        return new ResponseEntity<>("User created successfully.", HttpStatus.CREATED);
    }

    /**
     * Update an existing user.
     * 
     * @param username
     * @param updatedUser
     * @return ResponseEntity<String>
     */
    @PutMapping()
    public ResponseEntity<String> updateUser(@RequestParam("username") String username, @RequestBody UserDTO updatedUser) {
        
        if (!username.equals(updatedUser.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "An error occured while updating");
        }

        userService.updateUser(updatedUser);
        return new ResponseEntity<>("User Updated Successfully.", HttpStatus.NO_CONTENT);
    }
    
    /**
     * Delete a user. 
     * @param username
     * @return ResponseEntity<String>
     */
    @DeleteMapping() 
    public ResponseEntity<String> deleteUser(@RequestParam("username") String username) {
        userService.deleteUser(username);
        return new ResponseEntity<>("User Deleted Successfully", HttpStatus.NO_CONTENT);
    }
}
