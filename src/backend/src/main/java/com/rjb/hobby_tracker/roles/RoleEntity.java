package com.rjb.hobby_tracker.roles;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonFormat.Feature;
import com.rjb.hobby_tracker.privileges.PrivilegeEntity;
import com.rjb.hobby_tracker.users.UserEntity;

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
@Table(name = "ROLE")
public class RoleEntity {
    /**
     * Constructor by name and privileges.
     * @param name
     */
    public RoleEntity(String name, Collection<PrivilegeEntity> privileges) {
      this.name = name;
      this.privileges = privileges;
    }

    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Collection<UserEntity> users;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "roles_privileges", 
        joinColumns = @JoinColumn(
          name = "role_id", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(
          name = "privilege_id", referencedColumnName = "id"))
    private Collection<PrivilegeEntity> privileges;
}
