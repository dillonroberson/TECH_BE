package com.app.mb_clone.service;

import com.app.mb_clone.dto.request.PreExchangeRequest;
import com.app.mb_clone.model.Account;
import com.app.mb_clone.service.design.IGenericService;

import java.util.Optional;

public interface IAccountService extends IGenericService<Account> {
    Optional<Account> findAccountByBankAndNumber(Long bankId, String number);
    boolean registerPIN(Long userId, String pin);
    Optional<Account> findAccountByUserAndBank(Long userId, Long bankId);
}
