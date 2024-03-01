package com.app.mb_clone.controller;

import com.app.mb_clone.model.Bank;
import com.app.mb_clone.service.IBankService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/banks")
public class BankController {
    private final IBankService bankService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        List<Bank> banks = bankService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(banks);
    }
}
