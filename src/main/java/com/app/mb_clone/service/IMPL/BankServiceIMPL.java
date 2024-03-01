package com.app.mb_clone.service.IMPL;

import com.app.mb_clone.model.Bank;
import com.app.mb_clone.repository.IBankRepository;
import com.app.mb_clone.service.IBankService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BankServiceIMPL implements IBankService {
    private final IBankRepository bankRepository;
    @Override
    public List<Bank> findAll() {
        return bankRepository.findAll();
    }

    @Override
    public Bank findById(Long id) {
        return bankRepository.findById(id).orElseThrow(() -> new RuntimeException("Bank Not Found!"));
    }

    @Override
    public Bank save(Bank bank) {
        return bankRepository.save(bank);
    }

    @Override
    public void remove(Long id) {
        bankRepository.deleteById(id);
    }

    @Override
    public Bank findByName(String name) {
        return bankRepository.findByNameEqualsIgnoreCase(name).orElseThrow(() -> new RuntimeException("Bank Not Found!"));
    }

    @Override
    public Bank findBankByCode(String code) {
        return bankRepository.findBankByCode(code).orElseThrow(() -> new RuntimeException("Bank Not Found!"));
    }
}
