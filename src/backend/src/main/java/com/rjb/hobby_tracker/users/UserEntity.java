package com.rjb.hobby_tracker.users;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.rjb.hobby_tracker.roles.RoleEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "USER")
public class UserEntity implements UserDetails {
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(unique = true, nullable = false, updatable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable( 
        name = "users_roles", 
        joinColumns = @JoinColumn(
          name = "user_id", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(
          name = "role_id", referencedColumnName = "id")) 
    private Collection<RoleEntity> roles;

    @Column(unique = true, nullable = false)
    private String email;

    private String bio;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
      return getRoles().stream().map(r -> {
        List<GrantedAuthority> privileges = new ArrayList<>();
        privileges.add(new SimpleGrantedAuthority(r.getName()));
        privileges.addAll(r.getPrivileges().stream().map(p -> new SimpleGrantedAuthority(p.getName())).toList());
        
        return privileges;
      }).flatMap(List::stream)
      .collect(Collectors.toList());
    }
}
