package com.app.mb_clone.repository;

import com.app.mb_clone.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAccountRepository extends JpaRepository<Account, Long> {

    @Query(value = "select * from accounts where bank_id = ?1 and number like ?2", nativeQuery = true)
    Optional<Account> findAccountByBankAndNumber(Long bankId, String number);
    @Query(value = "select * from accounts where user_id = ?1 and bank_id = ?2",nativeQuery = true)
    Optional<Account> findAccountByUserAndBank(Long userId, Long bankId);
}
