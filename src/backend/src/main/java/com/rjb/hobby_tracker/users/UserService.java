package com.rjb.hobby_tracker.users;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.rjb.hobby_tracker.roles.RoleEntity;
import com.rjb.hobby_tracker.roles.RoleRepository;

import jakarta.transaction.Transactional;

/**
 * Service for CRUD activity on users.
 */
@Service
@Transactional
public class UserService {

    /** JPA User Repository. */
    private UserRepository userRepository;

    /** JPA Role Repository. */
    private RoleRepository roleRepository;

    /** ModelMapper for converting entity <--> dto. */
    private ModelMapper modelMapper;

    /** Password Encoder. */
    private PasswordEncoder passwordEncoder;

    /**
     * User service constructor. 
     * 
     * @param modelMapper
     * @param userRepository
     */
    public UserService(UserRepository userRepository, RoleRepository roleRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }
    
    /**
     * Find an individual user by username.
     * 
     * @param username
     * @return UserDTO or empty optional
     */
    public Optional<UserDTO> findByUsername(String username) {
        return userRepository.findByUsername(username).map(e -> modelMapper.map(e, UserDTO.class));
    }

    /**
     * Determine if a user with provided username already exists in the db. 
     * 
     * @param username
     * @return true if username exists, false otherwise.
     */
    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    /**
     * Determine if a user with provided email already exists in the db.
     * 
     * @param email
     * @return true if email exists, false otherwise.
     */
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * Return a list of users in the database. 
     * 
     * @return list of UserDTO objects
     */
    public List<UserDTO> findAll() {
        return userRepository.findAll()
            .stream()
            .map(e -> modelMapper.map(e, UserDTO.class))
            .toList();
    }

    /**
     * Add a new user to the database. 
     * 
     * @param newUser submitted user data.
     */
    public void createUser(UserDTO newUser) {
        if (findByUsername(newUser.getUsername()).isPresent()) {
            throw new DataIntegrityViolationException(String.format("Username %s already exists.", newUser.getUsername()));
        }

        if (emailExists(newUser.getEmail())) {
            throw new DataIntegrityViolationException(String.format("User with email %s already exists.", newUser.getEmail()));
        }

        // encode password
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        // retrieve user role if it exists in db
        RoleEntity userRole = roleRepository.findByName("ROLE_USER").orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Role not found"));

        // convert dto to entity
        UserEntity newUserEntity = modelMapper.map(newUser, UserEntity.class);
        // set base user role
        newUserEntity.setRoles(Arrays.asList(userRole));

        // save user to db
        userRepository.save(newUserEntity);
    }

    /**
     * Update an existing user. 
     * 
     * @param updatedUser submitted user data.
     */
    public void updateUser(UserDTO updatedUser) {
        if (findByUsername(updatedUser.getUsername()).isEmpty()) {
            throw new DataRetrievalFailureException(String.format("User %s not found.", updatedUser.getUsername()));
        }

        userRepository.save(modelMapper.map(updatedUser, UserEntity.class));
    }

    /**
     * Delete a user by id.
     * 
     * @param id of user entity
     */
    public void deleteUser(Integer id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User %s does not exist and cannot be deleted.", id));
        }
    }
}
