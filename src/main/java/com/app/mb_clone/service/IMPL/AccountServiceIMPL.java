package com.app.mb_clone.service.IMPL;

import com.app.mb_clone.dto.request.PreExchangeRequest;
import com.app.mb_clone.model.Account;
import com.app.mb_clone.model.Bank;
import com.app.mb_clone.repository.IAccountRepository;
import com.app.mb_clone.service.IAccountService;
import com.app.mb_clone.service.IBankService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AccountServiceIMPL implements IAccountService {
    private final IAccountRepository accountRepository;
    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account findById(Long id) {
        return accountRepository.findById(id).orElseThrow(()-> new RuntimeException("Account Not Found!"));
    }

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public void remove(Long id) {
        accountRepository.deleteById(id);
    }

    @Override
    public Optional<Account> findAccountByBankAndNumber(Long bankId, String number) {
        return accountRepository.findAccountByBankAndNumber(bankId, number);
    }

    @Override
    public boolean registerPIN(Long userId, String pin) {
        Optional<Account> accountOptional = findAccountByUserAndBank(userId, (long)1);
        if(!accountOptional.isPresent()) {
            return false;
        }
        Account account = accountOptional.get();
        account.setPIN(pin);
        save(account);
        return true;
    }

    @Override
    public Optional<Account> findAccountByUserAndBank(Long userId, Long bankId) {
        return accountRepository.findAccountByUserAndBank(userId, bankId);
    }


}
