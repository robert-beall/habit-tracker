package com.rjb.hobby_tracker.users;

import java.util.Collection;

import com.rjb.hobby_tracker.roles.RoleDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class UserDTO {
    private String username;

    private String password;

    private String email;

    private String bio;

    private Collection<RoleDTO> roles;
}
