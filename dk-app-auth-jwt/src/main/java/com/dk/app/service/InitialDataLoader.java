package com.dk.app.service;

import com.dk.app.model.Role;
import com.dk.app.model.RoleName;
import com.dk.app.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class InitialDataLoader {
	
	@Autowired
	private RoleRepository roleRepository;

	@Bean
	public ApplicationRunner initializer() {
		List<RoleName> roles = Arrays.asList(RoleName.ROLE_ADMIN, RoleName.ROLE_STUDENT, RoleName.ROLE_USER);
	    return args -> roles.forEach(i -> createRoleIfNotFound(i));
	}
	
	private Optional<Role> createRoleIfNotFound(RoleName roleName) {
        Optional<Role> role = roleRepository.findByName(roleName);
        if (!role.isPresent()) {
        	Role newRole = new Role();
        	newRole.setName(roleName);
        	newRole = roleRepository.save(newRole);
        }
        return role;
    }
}
