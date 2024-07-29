package com.rjb.hobby_tracker.roles;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
    /**
     * Find role entity by unique name. 
     * 
     * @param name
     * @return Optional of role
     */
    public Optional<RoleEntity> findByName(String name);
}
