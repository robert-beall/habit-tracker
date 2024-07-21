package com.rjb.hobby_tracker.users;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

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
            throw new DataIntegrityViolationException("User " + newUser.getUsername() + " already exists.");
        }

        userRepository.save(modelMapper.map(newUser, UserEntity.class));
    }
}
