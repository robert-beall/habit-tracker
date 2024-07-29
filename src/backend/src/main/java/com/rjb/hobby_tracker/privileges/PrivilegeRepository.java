package com.rjb.hobby_tracker.privileges;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<PrivilegeEntity, Integer> {
    /**
     * Find privilege by unique name. 
     * 
     * @param name
     * @return Optional of privilege
     */
    public Optional<PrivilegeEntity> findByName(String name);
}
