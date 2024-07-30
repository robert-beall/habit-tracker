package com.rjb.hobby_tracker.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rjb.hobby_tracker.users.UserDTO;
import com.rjb.hobby_tracker.users.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    /** User service */
    private UserService userService; 

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Endpoint for individual user signup.
     * 
     * @return status code 201
     */
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody UserDTO newUser) {
        userService.createUser(newUser);
        return new ResponseEntity<>("Sign up successful", HttpStatus.CREATED);
    }
}
