package com.rjb.hobby_tracker.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.rjb.hobby_tracker.roles.RoleEntity;
import com.rjb.hobby_tracker.users.UserEntity;

public class UserDetailsImpl implements UserDetails {
    private UserEntity user;

    public UserDetailsImpl(UserEntity userEntity) {
        this.user = userEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<RoleEntity> roles = user.getRoles().stream().toList();
        List<String> authorities = new ArrayList<>();

        for (RoleEntity role : roles) {
            System.out.println(role.getName());
            authorities.add(role.getName());
        }

        return authorities.stream().map(SimpleGrantedAuthority::new).toList();
        
        // user.getRoles().stream().map((role) -> {
        // List<GrantedAuthority> privileges = new ArrayList<>();
        // privileges.add(new SimpleGrantedAuthority(role.getName()));
        // privileges.addAll(role.getPrivileges().stream().map(privilege -> new SimpleGrantedAuthority(privilege.getName())).toList());
        //     return privileges;
        // }).flatMap(List::stream)
        // .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }
    
}
