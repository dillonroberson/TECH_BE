package com.app.mb_clone.service;

import com.app.mb_clone.model.Role;
import com.app.mb_clone.model.RoleName;
import com.app.mb_clone.service.design.IGenericService;

import java.util.Set;


public interface IRoleService extends IGenericService<Role> {
    Role findByName(RoleName name);
    Set<Role> registerRole(Set<String> stringRoles);
}
