package com.rjb.hobby_tracker.roles;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RoleService {
    private RoleRepository roleRepository;

    private ModelMapper modelMapper; 
    
    /**
     * RoleService constructor. 
     * 
     * @param roleRepository
     * @param modelMapper
     */
    public RoleService(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Determine if role with name exists. 
     * 
     * @param name
     * @return <i>true</i> if role exists, <i>false</i> if not
     */
    public boolean exists(String name) {
        return roleRepository.existsByName(name);
    }

    /**
     * Find a role by name.
     * 
     * @param name
     * @return RoleDTO or empty optional
     */
    public Optional<RoleDTO> findByName(String name) {
        return roleRepository.findByName(name).map(e -> modelMapper.map(e, RoleDTO.class));
    }

    /**
     * Return a list of roles.
     * 
     * @return list of RoleDTO objects
     */
    public List<RoleDTO> findAll() {
        return roleRepository.findAll()
            .stream()
            .map(e -> modelMapper.map(e, RoleDTO.class))
            .toList();
    }
}
