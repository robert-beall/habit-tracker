package com.rjb.hobby_tracker.setup;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.rjb.hobby_tracker.privileges.PrivilegeEntity;
import com.rjb.hobby_tracker.privileges.PrivilegeRepository;
import com.rjb.hobby_tracker.roles.RoleEntity;
import com.rjb.hobby_tracker.roles.RoleRepository;
import com.rjb.hobby_tracker.users.UserEntity;
import com.rjb.hobby_tracker.users.UserRepository;

import io.github.cdimascio.dotenv.Dotenv;

@Component
@Transactional
public class AuthSetup implements
  ApplicationListener<ContextRefreshedEvent> {
    boolean alreadySetup = false;

    Dotenv dotenv = Dotenv.load();

    private UserRepository userRepository;
 
    private RoleRepository roleRepository;
 
    private PrivilegeRepository privilegeRepository;
 
    private PasswordEncoder passwordEncoder;

    public AuthSetup(UserRepository userRepository, RoleRepository roleRepository, PrivilegeRepository privilegeRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.privilegeRepository = privilegeRepository;
        this.passwordEncoder = passwordEncoder;
    }
 
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
 
        if (alreadySetup)
            return;
        PrivilegeEntity readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
        PrivilegeEntity writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");
 
        List<PrivilegeEntity> adminPrivileges = Arrays.asList(
          readPrivilege, writePrivilege);
          
        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        createRoleIfNotFound("ROLE_USER", Arrays.asList(readPrivilege));

        RoleEntity adminRole = roleRepository.findByName("ROLE_ADMIN").orElseThrow(() -> new DataRetrievalFailureException("ROLE_ADMIN not found."));

        UserEntity user = new UserEntity();
        user.setPassword(passwordEncoder.encode(dotenv.get("ADMIN_PASSWORD")));
        user.setUsername(dotenv.get("ADMIN_USERNAME"));
        user.setEmail(dotenv.get("ADMIN_EMAIL"));
        user.setRoles(Arrays.asList(adminRole));
        userRepository.save(user);

        alreadySetup = true;
    }

    public PrivilegeEntity createPrivilegeIfNotFound(String name) {
        return privilegeRepository.findByName(name)
            .orElseGet(() -> privilegeRepository.save(new PrivilegeEntity(name)));
    }

    public RoleEntity createRoleIfNotFound(
      String name, Collection<PrivilegeEntity> privileges) {
        return roleRepository.findByName(name)
            .orElseGet(() -> roleRepository.save(new RoleEntity(name, privileges)));
    }
}
