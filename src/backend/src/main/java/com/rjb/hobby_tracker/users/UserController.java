package com.rjb.hobby_tracker.users;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


/**
 * <i>UserController</i> defines endpoints for handling user details from
 * an admin perspective. Endpoints include user creation, update, listing, and retrieval.
 */
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
    @GetMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserDTO> listUsers() {
        return userService.findAll();
    }

    /**
     * Find a user by username. 
     * 
     * @param username
     * @return UserDTO
     */
    @GetMapping(path="/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public UserDTO getUser (@PathVariable final String username) {
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
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
    @PutMapping("/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> updateUser(@PathVariable("username") String username, @RequestBody UserDTO updatedUser) {
        
        if (!username.equals(updatedUser.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "An error occured while updating");
        }

        userService.updateUser(updatedUser);
        return new ResponseEntity<>("User Updated Successfully.", HttpStatus.NO_CONTENT);
    }

    /**
     * Endpoint for assigning a role to a user. 
     * 
     * @param username
     * @param role
     * @return NO_CONTENT on success
     */
    @PutMapping("/role/{username}/{role}") 
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> assignRole(@PathVariable("username") String username, @PathVariable String role) {
        userService.assignRoleToUser(username, role);

        return new ResponseEntity<>(String.format("Role %s assigned to user %s", role, username), HttpStatus.NO_CONTENT);
    }

    /**
     * Endpoint for revoking a role from a user. 
     * 
     * @param username
     * @param role
     * @return NO_CONTENT on success
     */
    @DeleteMapping("/role/{username}/{role}") 
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> revokeRole(@PathVariable("username") String username, @PathVariable String role) {
        userService.revokeRoleFromUser(username, role);

        return new ResponseEntity<>(String.format("Role %s revoked from user %s", role, username), HttpStatus.NO_CONTENT);
    }
    
    /**
     * Delete a user. 
     * @param id of user
     * @return ResponseEntity<String>
     */
    @DeleteMapping() 
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteUser(@RequestParam("id") Integer id) {
        userService.deleteUser(id);
        return new ResponseEntity<>("User Deleted Successfully", HttpStatus.NO_CONTENT);
    }
}
