package com.rjb.hobby_tracker.users;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    /**
     * Find a user by username. 
     * 
     * @param username
     * @return Optional of UserEntity
     */
    public Optional<UserEntity> findByUsername(String username);

    /**
     * Determine if user with username value exists. 
     * 
     * @param username
     * @return <i>true</i> if user exists, <i>false</i> otherwise
     */
    public boolean existsByUsername(String username);
}
