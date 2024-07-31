package com.rjb.hobby_tracker.roles;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/roles")
public class RoleController {
    private RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }
    /**
     * Return a list of roles.
     * 
     * @return List of RoleDTO
     */
    @GetMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<RoleDTO> listUsers() {
        return roleService.findAll();
    }

    /**
     * Find a role by name. 
     * 
     * @param name
     * @return RoleDTO
     */
    @GetMapping(path="/{name}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public RoleDTO getRole (@PathVariable final String name) {
        Optional<RoleDTO> res = roleService.findByName(name);

        if (res.isPresent()) {
            return res.get();
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Role %s does not exist", name));
    }
}
