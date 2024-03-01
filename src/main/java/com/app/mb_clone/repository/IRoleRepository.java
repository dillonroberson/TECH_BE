package com.app.mb_clone.repository;

import com.app.mb_clone.model.Role;
import com.app.mb_clone.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName (RoleName name);
}
