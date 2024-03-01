package com.app.mb_clone.service;

import com.app.mb_clone.model.Bank;
import com.app.mb_clone.service.design.IGenericService;

public interface IBankService extends IGenericService<Bank> {
    Bank findByName(String name);
    Bank findBankByCode(String code);
}
