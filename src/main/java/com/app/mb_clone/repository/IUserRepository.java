package com.app.mb_clone.repository;

import com.app.mb_clone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);
    Optional<User> findByPhone(String phoneNumber);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
}
