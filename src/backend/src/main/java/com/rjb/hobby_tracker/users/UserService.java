package com.rjb.hobby_tracker.users;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {

    /** JPA User Repository. */
    private UserRepository userRepository;

    /** ModelMapper for converting entity <--> dto. */
    private ModelMapper modelMapper;

    /**
     * User service constructor. 
     * 
     * @param modelMapper
     * @param userRepository
     */
    public UserService(ModelMapper modelMapper, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
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
            throw new DataIntegrityViolationException(String.format("User %s already exists.", newUser.getUsername()));
        }

        userRepository.save(modelMapper.map(newUser, UserEntity.class));
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
