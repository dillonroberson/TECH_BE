package com.app.mb_clone.service.IMPL;

import com.app.mb_clone.model.Role;
import com.app.mb_clone.model.RoleName;
import com.app.mb_clone.repository.IRoleRepository;
import com.app.mb_clone.service.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleServiceIMPL implements IRoleService {
    private final IRoleRepository roleRepository;
    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role findById(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new RuntimeException("Role Not Found"));
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void remove(Long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public Role findByName(RoleName name) {
        return roleRepository.findByName(name).orElseThrow(() -> new RuntimeException("Role Not Found!"));
    }

    @Override
    public Set<Role> registerRole(Set<String> stringRoles) {
        Set<Role> roles = new HashSet<>();
        if (stringRoles == null) {
            Role role = findByName(RoleName.USER);
            roles.add(role);
        } else {
            stringRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = findByName(RoleName.ADMIN);
                        roles.add(adminRole);
                    case "pm":
                        Role pmRole = findByName(RoleName.PM);
                        roles.add(pmRole);
                    case "user":
                        Role userRole = findByName(RoleName.USER);
                        roles.add(userRole);
                }
            });
        }
        return roles;
    }
}
