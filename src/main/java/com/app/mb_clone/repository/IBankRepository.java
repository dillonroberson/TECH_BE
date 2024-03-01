package com.app.mb_clone.repository;

import com.app.mb_clone.model.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IBankRepository extends JpaRepository<Bank, Long> {
    Optional<Bank> findByNameEqualsIgnoreCase(String name);
    Optional<Bank> findBankByCode(String code);
}
