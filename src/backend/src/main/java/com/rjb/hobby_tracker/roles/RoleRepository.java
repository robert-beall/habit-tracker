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

    /**
     * Determine if role exists by provided name. 
     * 
     * @param name
     * @return <i>true</i> if role with name exists, <i>false</i> otherwise
     */
    public boolean existsByName(String name);
}
